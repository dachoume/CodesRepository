import tensorflow as tf

"""
测试创建会话session

sess = tf.InteractiveSession() 是创建一个交互式的环境，使用 Tensor.eval() 和 Operation.run() 方法代替 Session.run()
这样可以避免使用一个变量来持有会话
"""

bias = tf.constant(0.1, shape=[32])  # 注意这里使用的常量

with tf.Session() as sess:
    result = sess.run(bias)
    print(result)
