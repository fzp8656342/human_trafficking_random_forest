import urllib
from bs4 import BeautifulSoup
import requests
import pandas as pd
import csv
import codecs
import re
import os
import sys
from django.template.defaultfilters import length


tbltxt="#adf2324 #4fa56 #78df9566 #455asd625"
tbltxt = re.findall(r'#[\w]*',tbltxt)

test=os.listdir('D:/eccie_1000')
print(test[3])
for count in range(0,len(test)):
    soup = BeautifulSoup(open("D:/eccie_1000/"+test[count],encoding='utf-8'), "html.parser")
    out=[]
    tbltxt=""
    for table in soup.find_all('div',id='posts'):
        #print(table.text.encode(sys.stdin.encoding, "replace").decode(sys.stdin.encoding))
        """
        for link in soup.find_all('a',attrs={"href":re.compile(r'^http:')}):
            #print(link.get('href'))
            out.append(link.get('href'))
        """
        for td1 in table.find_all('td','alt1'):
            #print(td1.text.encode(sys.stdin.encoding, "replace").decode(sys.stdin.encoding))
            tbltxt = td1.text.encode(sys.stdin.encoding, "replace").decode(sys.stdin.encoding)
            #tbltxt = divi.text.encode(sys.stdin.encoding, "replace").decode(sys.stdin.encoding)
            tbltxt = tbltxt.replace("\n","")
            tbltxt = tbltxt.replace("\t","")
            tbltxt = tbltxt.replace(","," ")
            #tbltxt = tbltxt.replace(" ","")        
            #print(tbltxt)
            if(tbltxt is not None ):
                out.append(tbltxt)
        for td in table.find_all('td','alt2'):
            #print(td.text.encode(sys.stdin.encoding, "replace").decode(sys.stdin.encoding))
            for div in td.find_all('div','smallfont'):
                for divi in div.find_all('div'):
                    tbltxt = divi.text.encode(sys.stdin.encoding, "replace").decode(sys.stdin.encoding)
                    tbltxt = tbltxt.replace("\n","")
                    tbltxt = tbltxt.replace("\t","")
                    tbltxt = tbltxt.replace(" ","")
                    tbltxt = tbltxt.replace(","," ")
                    if(tbltxt is not None ):
                        out.append(tbltxt)
                    
                    #print(tbltxt)
                    #print(divi.text.encode(sys.stdin.encoding, "replace").decode(sys.stdin.encoding))
    
    #print(out)
    
    for table1 in soup.find_all('div',id='posts'):
        #print(table1.text.encode(sys.stdin.encoding, "replace").decode(sys.stdin.encoding))   
        for div in table1.find_all('div','center'):   
            print(div.text.encode(sys.stdin.encoding, "replace").decode(sys.stdin.encoding))   
            tbltxt = div.text.encode(sys.stdin.encoding, "replace").decode(sys.stdin.encoding)
            tbltxt = tbltxt.replace("\n","")
            tbltxt = tbltxt.replace("\t","")
            tbltxt = tbltxt.replace(","," ")
      
        #tbltxt = tbltxt.replace(" ","")
        if(tbltxt is not None):
            out.append(tbltxt)
            out.append('\n')
    
         
    #print(out)    
    if(out is not None):       
        with open('somefile.txt', 'a') as the_file:
            the_file.write(test[count]+'\n')
            for x in range(0,len(out)):
                the_file.write(out[x]+'\n')
    
    """
    with open('D:/outfile.csv','a') as the_file:
        the_file.write(test[count])
        for x in range(0,len(out)):
            the_file.write(out[x])
       
        #the_file.write(out[x])
     """
    #print(out.count(value))
    """
    if(out):
        with open('outfile.csv','a',newline='') as f:
            out.append(test[count])
            writer=csv.writer(f)
            writer.writerow(out)
    """ 
    #print(out)
    #print(out)   
    #print(table.text.encode(sys.stdin.encoding, "replace").decode(sys.stdin.encoding))      
    
        #for div in soup.find_all('div','smallfont'):
            #print(div.text.encode(sys.stdin.encoding, "replace").decode(sys.stdin.encoding))
    #for record in soup.findAll(('td', {'class' : 'alt2'})):
        #for div in soup.find('div', {'class' : 'smallfont'}):
    #print(type(table))
#print(str(out).replace(","," "))
    

