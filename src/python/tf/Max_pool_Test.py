import tensorflow as tf

a = tf.constant([
    [[1.0, 2.0, 3.0, 4.0],
     [5.0, 6.0, 7.0, 8.0],
     [8.0, 7.0, 6.0, 5.0],
     [4.0, 3.0, 2.0, 1.0]],
    [[4.0, 3.0, 2.0, 1.0],
     [8.0, 7.0, 6.0, 5.0],
     [1.0, 2.0, 3.0, 4.0],
     [5.0, 6.0, 7.0, 8.0]]
])

"""
reshape后（宽，高，通道）的需要竖着看（这种表示的都需要这么看）
通道1  1357
      8642
      4286
      1357

通道2  2468
      7531
      3175
      2468

"""
a = tf.reshape(a, [1, 4, 4, 2])  # 4*4*2 表示宽，高，通道数

pooling = tf.nn.max_pool(a, [1, 4, 1, 1], [1, 1, 1, 1], padding='VALID')
with tf.Session() as sess:
    print("image:")
    image = sess.run(a)
    print(image)
    print("reslut:")
    result = sess.run(pooling)
    print(result)
