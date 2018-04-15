# -*- coding: utf-8 -*-
"""
Created on Sat Apr 14 21:36:16 2018
This is a python file to clean data and create labels for the data
Using TF-IDF to convert sentences to bag of words
Train a randomforest/linear regression to classify the sentences
@author: fzp
"""

from sklearn.feature_extraction.text import TfidfVectorizer
import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import cross_validate
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split
def token(input_str):
    return " ".join(input_str)
vectorizer = TfidfVectorizer(ngram_range=(2, 4), stop_words='english', lowercase=True, smooth_idf=True)
file = open('C:/Users/fzp/Desktop/somefile.txt', 'r', encoding="ISO-8859-1").read()
result = file.split('\n\n\n\n\n')  #split each html to a list
out_csv = pd.read_csv("C:/Users/fzp/Desktop/out_out.csv")
out_csv = out_csv[out_csv.label<=1.00]
out_csv = out_csv.drop('Unnamed: 2', axis=1)



html_index = []
out_csv = out_csv.as_matrix()
label = {}
label_list = []
content = []
for dict_key in out_csv:
    label[dict_key[0]] = dict_key[1]
    
for j, i in enumerate(result):
    i = i.split('\n')
    if(len(i) < 3):
        continue
    print(j)
    if i[0] in label.keys():
        label_list.append(label[i[0]])
        content.append(i[1])
data = vectorizer.fit_transform(content).toarray()

X_train, X_test, y_train, y_test = train_test_split(\
    data, label_list, test_size=0.2, random_state=0)

clf = RandomForestClassifier(n_estimators=10)

scores = cross_validate(clf, data, label_list, scoring='roc_auc',cv=5)

clf.fit(X_train, y_train)

pre = clf.predict(X_test)
