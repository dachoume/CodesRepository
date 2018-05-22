import tensorflow as tf
import numpy as np

"""
Tensor数据类型测试
"""

"""
变量(tf.Variable   这是一个类)
"""
w1 = tf.Variable(initial_value=[1, 2, 3])
init_op = tf.global_variables_initializer()  # 变量需要定义全局参数初始化器，并执行run()方法（在14行）
with tf.Session() as sess:
    sess.run(init_op)
    result = sess.run(w1)
    print(result)

"""
常量（constant()  这是一个函数）
"""
b1 = tf.constant(123)
with tf.Session() as sess:
    result = sess.run(b1)
    print(result)

"""
占位符（placeholder()  这是一个函数）

placeholder的作用可以理解为占个位置，我并不知道这里将会是什么值，但是知道类型和形状等等一些信息，先把这些信息填进去占个位置，
然后以后用feed的方式来把这些数据“填”进去。返回的就是一个用来用来处理feeding一个值的tensor。 
那么feed的时候一般就会在你之后session的run（）方法中用到feed_dict这个参数了。这个参数的内容就是你要“喂”给那个placeholder的内容。

可以理解为就是需要输入的内容

不需要初始化全局参数
"""
x = tf.placeholder(tf.float32, shape=(1024, 1024))
y = tf.matmul(x, x)

with tf.Session() as sess:
    # print(sess.run(y))  # ERROR: will fail because x was not fed.
    rand_array = np.random.rand(1024, 1024)
    print(sess.run(y, feed_dict={x: rand_array}))  # Will succeed.  （只是喂数据进去，还有fetch取数据，就是print(sess.run(tensor))）
