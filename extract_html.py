# -*- coding: utf-8 -*-
"""
Created on Sat Apr 14 16:01:40 2018

@author: fzp
"""

import os
import re
from azure.storage.blob import BlockBlobService

# Uncomment the prefix for the source you would like to use
#source = "craigslist"
#source = "backpage"
source = "eccie"

# update this to get more or less pages returned
maxRecords = 10000

# store html files in /tmp.  If you want to store somewhere else change the /tmp but leave
# backpage.com and eccie.net
if not os.path.exists("D:/hackson/tmp/backpage.com"):
	os.makedirs("D:/hackson/tmp/backpage.com")
if not os.path.exists("D:/hackson/tmp/eccie.net"):
	os.makedirs("D:/hackson/tmp/eccie.net")
if not os.path.exists("D:/hackson/tmp/craigslist.com"):
	os.makedirs("D:/hackson/tmp/craigslist.com")
if not os.path.exists("D:/hackson/tmp/craigslist.org"):
	os.makedirs("D:/hackson/tmp/craigslist.org")
o
######################### main program ######################
imgRegex = re.compile(r'<img.*?/>')
thumbsRegex = re.compile(r'<div id="thumbs">.*?</div>')

def remove_img_tags(data):
	retString = imgRegex.sub('', data)
	retString = thumbsRegex.sub('', retString)
	return retString

block_blob_service = BlockBlobService(account_name="genarchive", sas_token=r"sv=2017-04-17&si=htmlarchive-RL-20170920&sr=c&sig=zXfbqKZ4cVe%2Byi%2FoOsg1s%2FGYoUMpANwFaW5bvF9%2FKEI%3D")

htmlarchive_container = "htmlarchive"

records = 0
# grab the initial list, 10 at a time
block_blob_list = block_blob_service.list_blobs(container_name=htmlarchive_container, prefix=source, num_results=10, marker=None)
while True:
	for block_blob in block_blob_list:
		records += 1
		print("\trecord #: " + str(records) + ", Blob name: " + block_blob.name)
		file_path = "D:/hackson/tmp/" + block_blob.name + ".html"

		current_blob_name = block_blob.name
		raw_html = block_blob_service.get_blob_to_text(container_name=htmlarchive_container,
			blob_name=current_blob_name).content

		raw_html = remove_img_tags(raw_html)

		with open(file_path, "w", encoding='utf-8') as text_file:
			text_file.write(raw_html)    
		
	marker = block_blob_list.next_marker
	if not marker or records >= maxRecords:
		break;

	# we're not done yet so grab 10 more
	block_blob_list = block_blob_service.list_blobs(container_name=htmlarchive_container, prefix=source, num_results=10, marker=marker)

print("exported " + str(records) + " pages for " + source)
