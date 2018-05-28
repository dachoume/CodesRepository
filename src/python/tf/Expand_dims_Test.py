import tensorflow as tf

a = tf.constant([[1.0, 2.0, 3.0, 4.0],
                 [5.0, 6.0, 7.0, 8.0]])

b = tf.expand_dims(a, -1)

with tf.Session() as sess:
    print(sess.run(a))
    print("image:")
    image = sess.run(b)
    print(image)
    print(image.shape)  # 变成单通道  (2, 4, 1)
