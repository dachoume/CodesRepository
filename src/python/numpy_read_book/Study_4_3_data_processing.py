import numpy as np

# 利用数组进行数据处理

# 将条件逻辑表述为数组运算
# numpy.where函数是三元表达式x if condition else y 的矢量化版本
xarr = np.array([1.1, 1.2, 1.3, 1.4, 1.5])
yarr = np.array([2.1, 2.2, 2.3, 2.4, 2.5])
cond = np.array([True, False, True, False, True])

print(np.where(cond, xarr, yarr))

# 也可以写成np.where(arr>0,2,-2)  如果元素大于0则置为2，小于0为-2

# 数学和统计方法
# 可以通过数组上的一组数学函数对整个数组或某个轴向的数据进行统计计算
arr_1 = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]])
# 聚合计算
print(arr_1.sum())  # 45
print(arr_1.sum(axis=0))  # [12 15 18]  用于计算该轴上的统计值 0为纵轴，1为横轴
print(arr_1.sum(axis=1))  # [ 6 15 24]
# 不聚合计算，返回一个由中间结果组成的数组，类似的还有cumprod
print(arr_1.cumsum(0))
# [[ 1  2  3]
#  [ 5  7  9]
#  [12 15 18]]

# 基本数组统计方法
# sum             对数组中全部或某轴向的元素求和。零长度的数组的sum为0
# mean            算数平均数。零长度的数组的mean为NaN
# std、var        分别为标准差和方差，自由度可调（默认为n）
# min、max        最小值和最大值
# argmin、argmax  分别为最小和最大元素的索引
# cumsum          所有元素的累积和
# cumprod         所有元素的累计积

# NumPy数组也可以通过sort方法就地排序
# 多维数组可以在任何一个轴向上进行排序，只需将轴编号传给sort即可，arr.sort(1)

# NumPy提供了一些针对一维ndarray的基本集合运算
# unique(x)  计算x中的唯一元素，并返回有序结果
# intersect1d(x,y)  计算x和y中的公共元素，并返回有序结果
# union1d(x,y)  计算x和y的并集，并返回有序结果
# in1d   得到一个表示“x的元素是否包含于y”的布尔型数组
# setdiff1d  集合的差，即元素在x中且不在y中
# setxor1d   集合的对称差，即存在于一个数组中但不同时存在于两个数组中的元素（异或）
