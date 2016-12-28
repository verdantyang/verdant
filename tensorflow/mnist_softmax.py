# """Functions for downloading and reading MNIST data."""
# from __future__ import absolute_import
# from __future__ import division
# from __future__ import print_function

# import gzip
# import os
# import tempfile

# import numpy
# from six.moves import urllib
# from six.moves import xrange  # pylint: disable=redefined-builtin
# import tensorflow as tf
# from tensorflow.contrib.learn.python.learn.datasets.mnist import read_data_sets

import tensorflow as tf
import tensorflow.examples.tutorials.mnist.input_data as input_data

# 导入数据
mnist = input_data.read_data_sets("data_MNIST/", one_hot=True)

# 占位符（输入任意数量的MNIST图像，每幅图像展成784维）
x = tf.placeholder(tf.float32, [None, 784])
y_ = tf.placeholder("float", [None,10])

# 用784维的图片向量乘以它以得到一个10维的证据值向量，每一位对应不同数字类
W = tf.Variable(tf.zeros([784,10]))
# 偏移量
b = tf.Variable(tf.zeros([10]))

# 使用softmax算法
y = tf.nn.softmax(tf.matmul(x,W) + b)

# 评价指标：交叉熵
cross_entropy = -tf.reduce_sum(y_*tf.log(y))
train_step = tf.train.GradientDescentOptimizer(0.01).minimize(cross_entropy)

# 初始化
init = tf.global_variables_initializer()
sess = tf.Session()
sess.run(init)

# 训练模型
for i in range(1000):
	batch_xs, batch_ys = mnist.train.next_batch(100)
	sess.run(train_step, feed_dict={x: batch_xs, y_: batch_ys})

# 评估模型
correct_prediction = tf.equal(tf.argmax(y,1), tf.argmax(y_,1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction, "float"))
print(sess.run(accuracy, feed_dict={x: mnist.test.images, y_: mnist.test.labels}))