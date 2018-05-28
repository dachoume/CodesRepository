import tensorflow as tf
import numpy as np

data = np.float32(np.random.randn(100, 100))

x_data = tf.constant(data)

x_data = tf.reshape(x_data, [1, 100, 100, 1])

w_data = tf.Variable(tf.truncated_normal([3, 100, 1, 1], stddev=0.1))

y = tf.nn.conv2d(x_data, w_data, strides=[1, 1, 1, 1], padding='VALID')

with tf.Session() as sess:
    sess.run(tf.global_variables_initializer())
    print(sess.run(x_data).shape)
    print(sess.run(y).shape)
