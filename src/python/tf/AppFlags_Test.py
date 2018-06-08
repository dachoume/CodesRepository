import tensorflow as tf

FLAGS = tf.app.flags.FLAGS
tf.app.flags.DEFINE_float(
    'flag_float', 0.01, 'input a float')
tf.app.flags.DEFINE_integer(
    'flag_int', 400, 'input a int')
tf.app.flags.DEFINE_boolean(
    'flag_bool', True, 'input a bool')
tf.app.flags.DEFINE_string(
    'flag_string', 'yes', 'input a string')

print(FLAGS.flag_float)
print(FLAGS.flag_int)
print(FLAGS.flag_bool)
print(FLAGS.flag_string)
# 可以在调用脚本时传入参数 如：--flag_float 0.02 --flag_int 12，则结果为
# 0.02
# 12
# True
# yes
