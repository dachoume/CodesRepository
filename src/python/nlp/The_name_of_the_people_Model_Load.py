import gensim
import numpy as np

model = gensim.models.KeyedVectors.load_word2vec_format("../resources/in_the_name_of_people.model", binary=False)

arr_test = model['李达康']

print(arr_test)

arr = np.array(arr_test)
print(arr.shape)