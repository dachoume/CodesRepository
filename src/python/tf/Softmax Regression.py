import tensorflow as tf
from tensorflow.examples.tutorials.mnist import input_data

mnist = input_data.read_data_sets("MNIST_data/", one_hot=True)

# print(mnist.train.images.shape, mnist.train.labels.shape)

sess = tf.InteractiveSession()  # 将这个session注册为默认的session，之后的运算跑在这个session中，不同的session之间的数据和运算是独立的

x = tf.placeholder(tf.float32, [None, 784])  # placeholder是输入数据的地方，第一个是数据类型，none为数据条数无限制，784为数据的维度
w = tf.Variable(tf.zeros([784, 10]))  # variable用来存储模型参数的，持久化 784表示维度，10表示类别数
b = tf.Variable(tf.zeros([10]))

y = tf.nn.softmax(tf.matmul(x, w) + b)  # softmax函数模拟拟合值，y=softmax(Wx+b)

y_ = tf.placeholder(tf.float32, [None, 10])  # 实际值

# 损失函数，tf.reduce_sum为求和的西格玛，tf.reduce_mean则用来对每个batch数据结果求平均值
cross_entropy = tf.reduce_mean(-tf.reduce_sum(y_ * tf.log(y), reduction_indices=[1]))

# 训练器，只需要每次训练的时候feed数据就好，0.5为训练速率（步长）
train_step = tf.train.GradientDescentOptimizer(0.5).minimize(cross_entropy)

# 全局参数初始化器，并执行run方法
tf.global_variables_initializer().run()

for i in range(1000):
    batch_xs, batch_ys = mnist.train.next_batch(100)  # 每次都随机抽取100样本，所以是随机梯度下降
    train_step.run({x: batch_xs, y_: batch_ys})

# tf.argmax表示找到该tensor中最大值的索引
correct_prediction = tf.equal(tf.argmax(y, 1), tf.argmax(y_, 1)) # 1表示水平方向，0表示竖直方向

accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))

# 将测试数据特征和label输入评测流程accuracy
print(accuracy.eval({x: mnist.test.images, y_: mnist.test.labels}))
