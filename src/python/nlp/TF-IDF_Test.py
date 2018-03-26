from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_extraction.text import CountVectorizer

corpus = ["I come to China to travel",
          "This is a car polupar in China",
          "I love tea and Apple ",
          "The work is to write some papers in science"]

tfidf2 = TfidfVectorizer()
re = tfidf2.fit_transform(corpus)
print(re)

vectoorizer = CountVectorizer()

print(vectoorizer.fit_transform(corpus).toarray())

print(vectoorizer.get_feature_names())

==============================================================
  (0, 4)	0.4424621378947393
  (0, 15)	0.697684463383976
  (0, 3)	0.348842231691988
  (0, 16)	0.4424621378947393
  (1, 3)	0.3574550433419527
  (1, 14)	0.45338639737285463
  (1, 6)	0.3574550433419527
  (1, 2)	0.45338639737285463
  (1, 9)	0.45338639737285463
  (1, 5)	0.3574550433419527
  (2, 7)	0.5
  (2, 12)	0.5
  (2, 0)	0.5
  (2, 1)	0.5
  (3, 15)	0.2811316284405006
  (3, 6)	0.2811316284405006
  (3, 5)	0.2811316284405006
  (3, 13)	0.3565798233381452
  (3, 17)	0.3565798233381452
  (3, 18)	0.3565798233381452
  (3, 11)	0.3565798233381452
  (3, 8)	0.3565798233381452
  (3, 10)	0.3565798233381452
  
[[0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 2 1 0 0]
 [0 0 1 1 0 1 1 0 0 1 0 0 0 0 1 0 0 0 0]
 [1 1 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0]
 [0 0 0 0 0 1 1 0 1 0 1 1 0 1 0 1 0 1 1]]
 
['and', 'apple', 'car', 'china', 'come', 'in', 'is', 'love', 'papers', 'polupar', 'science', 'some', 'tea', 'the', 'this', 'to', 'travel', 'work', 'write']


