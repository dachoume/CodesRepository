from pandas import Series, DataFrame
import pandas as pd
import numpy as np

# 介绍操作Series和DataFrame中的数据的基本手段


# 重新索引 reindex
obj = Series([4.5, -4.7, 2.7, -1.5], index=['b', 'd', 'a', 'c'])
print(obj)
# b    4.5
# d   -4.7
# a    2.7
# c   -1.5

obj2 = obj.reindex(['a', 'b', 'c', 'd', 'e'])  # 会根据新索引进行重排。如果某个索引值当前不存在，就引入缺失值
print(obj2)
# a    2.7
# b    4.5
# c   -1.5
# d   -4.7
# e    NaN

print(obj.reindex(['a', 'b', 'c', 'd', 'e'], fill_value=0))  # 指定索引不存在的值
# a    2.7
# b    4.5
# c   -1.5
# d   -4.7
# e    0.0

# 对于时间序列这样的有序数据，重新索引时可能需要做一些插值处理
# method选项即可达到此目的，例如使用ffill可以实现向前值填充
obj3 = Series(['a', 'b', 'c', ], index=[0, 2, 4])
print(obj3.reindex(range(0, 6), method='ffill'))
# 0    a
# 1    a
# 2    b
# 3    b
# 4    c
# 5    c

# 对于DataFrame，reindex可以修改（行）索引、列，或两个都修改
# 如果仅传入一个序列，则会重新索引行
frame = DataFrame(np.arange(9).reshape((3, 3)), index=['a', 'c', 'd'], columns=['col1', 'col2', 'col3'])  # 关于np的以后再看
print(frame)
frame1 = frame
frame2 = DataFrame(np.arange(9).reshape((3, 3)), index=['a', 'c', 'd'], columns=['col1', 'col2', 'col3'])
#    col1  col2  col3
# a     0     1     2
# c     3     4     5
# d     6     7     8

print(frame.reindex(['a', 'b', 'c', 'd']))
#    col1  col2  col3
# a   0.0   1.0   2.0
# b   NaN   NaN   NaN
# c   3.0   4.0   5.0
# d   6.0   7.0   8.0

# 使用columns关键字可以重新索引列
states = ['apple', 'col1', 'col2']
print(frame.reindex(columns=states))
#    apple  col1  col2
# a    NaN     0     1
# c    NaN     3     4
# d    NaN     6     7

# 可以同时对行和列进行重新索引，而插值则只能按行应用（即轴0）
print(frame.reindex(index=['a', 'b', 'c', 'd'], method='ffill', columns=states))
#    apple  col1  col2
# a    NaN     0     1
# b    NaN     0     1
# c    NaN     3     4
# d    NaN     6     7

# 使用ix的标签索引功能，重新索引任务可以变得更简介
print(frame.ix[['a', 'b', 'c', 'd'], states])
#    apple  col1  col2
# a    NaN   0.0   1.0
# b    NaN   NaN   NaN
# c    NaN   3.0   4.0
# d    NaN   6.0   7.0

# 丢弃指定轴上的项，需要一个索引数组或列表
# 丢弃指定轴上的项
print(obj.drop('c'))
# b    4.5
# d   -4.7
# a    2.7

print(obj.drop(['a', 'b']))
# d   -4.7
# c   -1.5

# 对于DataFrame，可以删除任意轴上的索引值
print(frame.drop('a'))
#    col1  col2  col3
# c     3     4     5
# d     6     7     8

print(frame.drop('col2', axis=1))  # 如果axis=0，则沿着纵轴进行操作；axis=1，则沿着横轴进行操作
#    col1  col3
# a     0     2
# c     3     5
# d     6     8

# 索引、选取和过滤
# 可以利用索引切片的方式获取值，但是这里的切片末端时包含的
print(obj['a':'c'])
# a    2.7
# c   -1.5

# 可以通过索引切片设值
obj['a':'c'] = 5
print(obj)
# b    4.5
# d   -4.7
# a    5.0
# c    5.0

# DataFrame根据索引获取值
print(frame[['col1', 'col3']])  # 双括号，因为中间时列表，如果时一个列的话就不需要双括号了，如frame['col2']
#    col1  col3
# a     0     2
# c     3     5
# d     6     8

frame[frame < 5] = 0
print(frame)
#    col1  col2  col3
# a     0     0     0
# c     0     0     5
# d     6     7     8

print(frame[frame['col2'] > 0])
#    col1  col2  col3
# d     6     7     8

# 使用索引字段ix选取行和列的子集
print(frame1.ix[['a', 'c'], ['col1', 'col2']])
#    col1  col2
# a     0     0
# c     0     0

print(frame2.ix[['a', 'c'], ['col1', 'col2']])  # 注意上下两组数据的区别，可以看出Python是传引用
#    col1  col2
# a     0     1
# c     3     4

print(frame2.ix[2])  # 第三行
# col1    6
# col2    7
# col3    8

# DataFrame的索引选项
# obj[val]                  选取DataFrame的单个列或一组列
# obj.ix[val]               选取DataFrame的单个行或一组行
# obj.ix[:,val]             选取单个列或列子集
# obj.ix[val1,val2]         同时选取行和列
# reindex                   将一个或多个轴匹配到新索引
# xs                        根据整数位置选取单列或单行，并返回一个Series
# icol、irow方法             根据整数位置选取单列或单行，并返回一个Series
# get_value、set_value方法   根据行标签和列标签选取单个值

# 算数运算和数据对齐
# 在将对象相加时，如果存在不同的索引对，则结果的索引就是该索引对的并集
s1 = Series([7.3, 2.6, -1.5, -5.8], index=['a', 'c', 'd', 'e'])
s2 = Series([-2.1, -2.6, 1.0, 3.3, 1.0], index=['a', 'c', 'e', 'f', 'g'])
print(s1 + s2)
# a    5.2
# c    0.0
# d    NaN
# e   -4.8
# f    NaN
# g    NaN
# 自动的数据对齐操作在不重叠的索引处引入了NA值。缺失值会在算数运算过程中传播

# 对于DataFrame，对齐操作会同时放生在行和列上
df1 = DataFrame(np.arange(9.).reshape((3, 3)), columns=list('bcd'), index=['aaa', 'bbb', 'ccc'])
df2 = DataFrame(np.arange(12.).reshape((4, 3)), columns=list('bde'), index=['aaa', 'ddd', 'ccc', 'ddd'])
print(df1 + df2)
#         b   c     d   e
# aaa   0.0 NaN   3.0 NaN
# bbb   NaN NaN   NaN NaN
# ccc  12.0 NaN  15.0 NaN
# ddd   NaN NaN   NaN NaN
# ddd   NaN NaN   NaN NaN
# 把它们相加后将会返回一个新的DataFrame，其索引和列为原来那两个DataFrame的并集

# 使用df1的add方法，传入df2以及一个fill_value参数
print(df1.add(df2, fill_value=0))
#         b    c     d     e
# aaa   0.0  1.0   3.0   2.0
# bbb   3.0  4.0   5.0   NaN
# ccc  12.0  7.0  15.0   8.0
# ddd   3.0  NaN   4.0   5.0
# ddd   9.0  NaN  10.0  11.0

# 于此类似，在对Series或DataFrame重新索引时，也可以指定一个填充值

# add  加法
# sub  减法
# div  除法
# mul  乘法

# DataFrame和Series之间的运算
frame3 = DataFrame(np.arange(12.).reshape((4, 3)), columns=list('bde'), index=['apple', 'orange', 'banana', 'peach'])
print(frame3)
#           b     d     e
# apple   0.0   1.0   2.0
# orange  3.0   4.0   5.0
# banana  6.0   7.0   8.0
# peach   9.0  10.0  11.0

series3 = frame3.ix[0]  # ix是用来拿行的
print(series3)
# b    0.0
# d    1.0
# e    2.0
# Name: apple, dtype: float64

print(frame3 - series3)
#           b    d    e
# apple   0.0  0.0  0.0
# orange  3.0  3.0  3.0
# banana  6.0  6.0  6.0
# peach   9.0  9.0  9.0
# 默认情况下，DataFrame和Series之间的算数运算会将Series的索引匹配到DataFrame的列，然后沿着行一直向下广播
# 其实就是广播成这样：
#           b    d    e
# apple   0.0  1.0  2.0
# orange  0.0  1.0  2.0
# banana  0.0  1.0  2.0
# peach   0.0  1.0  2.0

# 如果希望匹配行切在列上广播，则必须使用算术运算方法
series3_1 = frame3['d']
frame4 = frame3.sub(series3_1, axis=0)
print(frame4)
#           b    d    e
# apple  -1.0  0.0  1.0
# orange -1.0  0.0  1.0
# banana -1.0  0.0  1.0
# peach  -1.0  0.0  1.0
# 传入的轴号就是希望匹配的轴

# 函数应用和映射
print(np.abs(frame4))  # 绝对值
#           b    d    e
# apple   1.0  0.0  1.0
# orange  1.0  0.0  1.0
# banana  1.0  0.0  1.0
# peach   1.0  0.0  1.0

# 将函数应用到由各列或行所形成的一维数组上，使用DataFrame的apply方法
frame5 = DataFrame(np.random.rand(4, 3), columns=list('abc'))
print(frame5)
#           a         b         c
# 0  0.994677  0.411195  0.721519
# 1  0.536137  0.926673  0.087266
# 2  0.302324  0.024903  0.303081
# 3  0.889727  0.292648  0.429763
f_1 = lambda x: x.max() - x.min()
print(frame5.apply(f_1))  # 结果看看就行，随机的数每次都不一样
# a    0.895099
# b    0.404342
# c    0.334877

print(frame5.apply(f_1, axis=1))  # 结果看看就行，随机的数每次都不一样


# 0    0.418475
# 1    0.861520
# 2    0.604604
# 3    0.435813

# 许多最为常见的数组统计功能都被实现成DataFrame的方法（如sum和mean），因此无需使用apply方法
# 除标量值外，传递给apply的函数还可以返回由多个值组成的Series：
def f_2(x):
    return Series([x.min(), x.max()], index=['min', 'max'])


print(frame5.apply(f_2))
#             a         b         c
# min  0.113664  0.279350  0.237196
# max  0.822776  0.886434  0.944179

f_3 = lambda x: '%.2f' % x
print(frame5.applymap(f_3))
#       a     b     c
# 0  0.37  0.13  0.11
# 1  0.12  0.63  0.21
# 2  0.37  0.73  0.87
# 3  0.98  0.40  0.02

print(frame5['a'].map(f_3))
# 0    0.58
# 1    0.66
# 2    0.86
# 3    0.71
# Name: a, dtype: object

# apply       当想让方程作用在一维的向量上时，可以使用apply来完成
# applymap    如果想让方程作用于DataFrame中的每一个元素，可以使用applymap()
# map         map()只要是作用将函数作用于一个Series的每一个元素

# 排序和排名

# 对行或列索引进行排序，可使用sort_index方法，它将返回一个已排序的新对象
# obj.sort_index()
# frame.sort_index()
# frame.sort_index(axis=1)

# 数据默认是按升序排序的，但也可以降序排序
# frame.sort_index(axis=1, ascending=False)

# 若要按值对Series进行排序，可使用其order方法（order方法好像过时了，这里使用sort_values()方法，注意函数后面的括号）
o_1 = Series([4, 10, 7, -1.5])
print(o_1.sort_values())
# 3    -1.5
# 0     4.0
# 2     7.0
# 1    10.0
# dtype: float64

# 在排序时，任何缺失值都会被放到Series的末尾

# 在DataFrame上，可以根据一个或多个列中的值进行排序，将一个或多个列的名字传递给by选项即可达到目的
frame6 = DataFrame({'b': [4, 7, -3, 2], 'a': [4, 1, 5, 9]})
print(frame6.sort_values(by=['a', 'b']))
#    a  b
# 1  1  7
# 0  4  4
# 2  5 -3
# 3  9  2

# rank和排序关系密切，会增设一个排名值，破坏平级关系
o_2 = Series([7, -5, 7, 4, 2, 0, 4])
print(o_2.rank())
# 0    6.5
# 1    1.0
# 2    6.5
# 3    4.5
# 4    3.0
# 5    2.0
# 6    4.5
# dtype: float64

# 根据值在原数据中出现的顺序给出排名
print(o_2.rank(method='first'))
# 0    6.0
# 1    1.0
# 2    7.0
# 3    4.0
# 4    3.0
# 5    2.0
# 6    5.0
# dtype: float64

# 按降序进行排名
print(o_2.rank(ascending=False))
# 0    1.5
# 1    7.0
# 2    1.5
# 3    3.5
# 4    5.0
# 5    6.0
# 6    3.5
# dtype: float64

print(o_2.rank(ascending=False, method='min'))
# 0    1.0
# 1    7.0
# 2    1.0
# 3    3.0
# 4    5.0
# 5    6.0
# 6    3.0
# dtype: float64

print(o_2.rank(ascending=False, method='dense'))
# 0    1.0
# 1    5.0
# 2    1.0
# 3    2.0
# 4    3.0
# 5    4.0
# 6    2.0
# dtype: float64

# DataFrame可以在行或列上计算排名
# frame.rank(axis=1)

# 排名时破环平级关系的method选项
# 'average'  默认，在相等分组中，为各个值平均分配排名
# 'min'      使用整个分组的最小排名
# 'max'      使用整个分组的做大排名
# 'first'    按值在原始数据中的出现顺序分配排名
# 'dense'    like ‘min’, but rank always increases by 1 between groups

# 带有重复值的轴索引
obj_1 = Series(range(5), index=['a', 'a', 'b', 'b', 'c'])
print(obj_1)
# a    0
# a    1
# b    2
# b    3
# c    4
# dtype: int32

# 索引的is_unique属性知道它的值是否是唯一的
print(obj_1.index.is_unique)
# False

# 带有重复值的索引，数据的选取行为将会有些不同
# 如果某个索引对应多个值，则返回一个Series
# 而对应单个值得，则返回一个标量值
print(obj_1['a'])
# a    0
# a    1
# dtype: int32

print(obj_1['c'])
# 4

# 对DataFrame的行进行索引时也是如此
df_1 = DataFrame(np.random.rand(4, 3), index=['a', 'a', 'b', 'c'])
print(df_1.ix['a'])
#           0         1         2
# a  0.884723  0.106233  0.580491
# a  0.830027  0.272732  0.315977
