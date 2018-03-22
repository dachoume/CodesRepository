#!/usr/bin/python
# -*- coding: utf-8 -*-

from pandas import Series, DataFrame
import pandas as pd
import numpy as np

# 汇总和计算描述统计

frame1 = DataFrame(np.arange(9).reshape((3, 3)), index=['a', 'c', 'd'], columns=['col1', 'col2', 'col3'])
print(frame1)
#    col1  col2  col3
# a     0     1     2
# c     3     4     5
# d     6     7     8

# 调用DataFrame的sum方法将会返回一个含有列小计的Series
print(frame1.sum())
# col1     9
# col2    12
# col3    15
# dtype: int64

# 传入axis=1将会按行进行求和运算
print(frame1.sum(axis=1))
# a     3
# c    12
# d    21
# dtype: int64

# NA值会自动被排除，除非整个切片（这里指的是行或列）都是NA。通过skipna选项可以禁用该功能（存在一个NA该结果就是NA）
# frame.mean(axis=1,skipna=False)

# describe用于一次性产生多个汇总统计
print(frame1.describe())
#        col1  col2  col3
# count   3.0   3.0   3.0
# mean    3.0   4.0   5.0
# std     3.0   3.0   3.0
# min     0.0   1.0   2.0
# 25%     1.5   2.5   3.5
# 50%     3.0   4.0   5.0
# 75%     4.5   5.5   6.5
# max     6.0   7.0   8.0

# 而对于非数值型数据，describe会产生另外一种汇总统计
# count unique top freq

# 描述和汇总统计
# count            非NA值的数量
# describe         针对Series或各DataFrame列计算汇总统计
# min、max         计算最小值和最大值
# argmin、argmax   计算能够获取到最小值和最大值的索引位置（整数）
# idxmin、idxmax   计算能够获取到最小值和最大值的索引值
# quantile         计算样本的分位数（0到1）
# sum              值的总和
# mean             值的平均数
# median           值的算术中位数（50%分位数）
# mad              根据平均值计算平均绝对离差
# var              样本值的方差
# std              样本值的标准差
# skew             样本值的偏度（三阶矩）
# kurt             样本值的峰度（四阶矩）
# cumsum           样本值的累计和
# cummin、cummax   样本值的累计最大值和累计最小值
# cumprod          样本值的累计积
# diff             计算一阶差分（对时间序列很有用）
# pct_change       计算百分数变化

# 唯一值、值计数、以及成员资格
# 有一类方法可以从一维Series的值中抽取信息
s_1 = Series(['a', 'a', 'c', 'c', 'b', 'b', 'd', 'f'])

# unique，可以得到Series中的唯一值数组
print(s_1.unique())  # 未排序
# ['a' 'c' 'b' 'd' 'f']

# value_counts用于计算一个Series中值出现的频率
print(s_1.value_counts())
# c    2
# a    2
# b    2
# d    1
# f    1
# dtype: int64

# isin,用于判断矢量化集合的成员资格
mask = s_1.isin(['b', 'c'])
print(mask)
# 0    False
# 1    False
# 2     True
# 3     True
# 4     True
# 5     True
# 6    False
# 7    False
# dtype: bool

print(s_1[mask])
# 2    c
# 3    c
# 4    b
# 5    b
# dtype: object

# isin   计算一个表示“Series各值是否包含传入的值序列中”的布尔型数组
# unique 计算Series中的唯一值数组，按发现的顺序返回
# value_counts   返回一个Series，其索引为唯一值，其值为频率，按计数值降序排列
