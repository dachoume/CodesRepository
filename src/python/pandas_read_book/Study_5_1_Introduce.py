from pandas import Series, DataFrame
import pandas as pd

# pandas中的两个主要数据结构，Series和DataFrame


# Series
# Series是一种类似于一维数组的对象，它由一组数据以及一组与之相关的数据标签（即索引）组成
obj = Series([4, -7, 9, 11])

print(obj)

# 0     4
# 1    -7
# 2     9
# 3    11
# 由于没有为数据指定索引，于是会自动创建一个0到N-1的整数型索引
# 可以通过Series的values和index属性获取其数组表示形式和索引对象
print(obj.values)  # [ 4 -7  9 11]
print(obj.index)  # RangeIndex(start=0, stop=4, step=1)

# 通常希望创建的Series带有一个可以对各个数据点进行标记的索引
obj2 = Series([4, -7, 9, 11], index=['d', 'b', 'a', 'c'])

print(obj2)
# d     4
# b    -7
# a     9
# c    11

# 可以通过索引的方式选取Series中的单个或一组值
print(obj2['a'])  # 9
print(obj2[['a', 'b', 'c']])  # 注意是双方括号
# a     9
# b    -7
# c    11

# 数组运算（根据布尔型数组进行过滤、标量乘法、应用数学函数等）都会保留索引和值之间的链接
print(obj2[obj2 > 0])
# d     4
# a     9
# c    11
print(obj2 * 2)
# d     8
# b   -14
# a    18
# c    22

# 可以将Series看成一个定长的有序字典，因为它是索引值到数据值的一个映射
print('a' in obj2)  # True

# 如果数据被存放在一个字典中，可以直接通过这个字典来创建Series
# 如果只传入字典，则结果Series中的索引就是原字典的键（有序排列）
sdata = {'aaa': 100, 'bbb': 200, 'ccc': 300}
obj3 = Series(sdata)
print(obj3)
# aaa    100
# bbb    200
# ccc    300

state = ['bbb', 'ccc', 'ddd']
obj4 = Series(sdata, state)
print(obj4)
# bbb    200.0
# ccc    300.0
# ddd      NaN   这个表示缺失数据
# 能对应上的就对应，对应不上的就用缺失表示

# isnull和notnull函数可以用于检测缺失数据（pd版,Series的没明白）
print(pd.isnull(obj4))
# bbb    False
# ccc    False
# ddd     True

# print(obj4.isnull)

# Series可以在运算中自动对齐不同索引的数据
print(obj3 + obj4)
# aaa      NaN
# bbb    400.0
# ccc    600.0
# ddd      NaN

# Series对象本身及其索引都有一个name属性，该属性和pandas其他关键功能关系密切
obj.name = 'num'
obj.index.name = 'state'
print(obj)
# state
# 0     4
# 1    -7
# 2     9
# 3    11
# Name: num, dtype: int64

# Series的索引可以通过赋值的方式进行修改
obj.index = ['kate', 'cat', 'dog', 'doll']
print(obj)
# kate     4
# cat     -7
# dog      9
# doll    11
# Name: num, dtype: int64
print('=======================================================================')

# DateFrame
# DataFrame是一个表格型的数据结构，它含有一组有序的列，每列可以是不同的值类型
# DataFrame既有行索引也有列索引，它可以被看做由Series组成的字典（共用一个索引）

# 构建DataFrame的办法最常用的一种是直接传入一个由等长列表或NumPy数组组成的字典
data = {'state': ['aaa', 'bbb', 'ccc', 'ddd', 'eee'], 'year': [2000, 2001, 2002, 2003, 2004],
        'pop': [1.0, 1.1, 1.2, 1.3, 1.4]}

frame = DataFrame(data)
print(frame)  # DataFrame会自动加上索引（和Series一样），且全部列会被有序排列
#    pop state  year
# 0  1.0   aaa  2000
# 1  1.1   bbb  2001
# 2  1.2   ccc  2002
# 3  1.3   ddd  2003
# 4  1.4   eee  2004

# 如果指定了列序列，则DataFrame的列就会按照指定顺序进行排列
print(DataFrame(data, columns=['year', 'state', 'pop']))
#    year state  pop
# 0  2000   aaa  1.0
# 1  2001   bbb  1.1
# 2  2002   ccc  1.2
# 3  2003   ddd  1.3
# 4  2004   eee  1.4

# 和Series一样，如果传入的列在数据中找不到，就会产生NA值
frame2 = DataFrame(data, columns=['year', 'state', 'pop', 'debt'], index=['one', 'two', 'three', 'four', 'five'])
print(frame2)
#        year state  pop debt
# one    2000   aaa  1.0  NaN
# two    2001   bbb  1.1  NaN
# three  2002   ccc  1.2  NaN
# four   2003   ddd  1.3  NaN
# five   2004   eee  1.4  NaN

# 通过类似字典标记的方式或属性的方式，可以将DataFrame的列获取为一个Series
print(frame['state'])
# 0    aaa
# 1    bbb
# 2    ccc
# 3    ddd
# 4    eee
# Name: state, dtype: object

# 行也可以通过位置或名称的方式进行获取
print(frame.ix[3])
# pop       1.3
# state     ddd
# year     2003
# Name: 3, dtype: object

# 列可以通过赋值的方式进行修改
frame3 = frame2
frame2['debt'] = 16.5
print(frame2)
#        year state  pop  debt
# one    2000   aaa  1.0  16.5
# two    2001   bbb  1.1  16.5
# three  2002   ccc  1.2  16.5
# four   2003   ddd  1.3  16.5
# five   2004   eee  1.4  16.5

# 将列表或数组赋值给某个列时，其长度不许跟DateFrame的长度相匹配。
# 如果赋值的时一个Series，就会精确匹配DataFrame的索引，所有的空位都将被填上缺失值
val = Series([-1.2, -1.5, -1.7], index=['two', 'four', 'three'])
frame3['debt'] = val
print(frame3)
#        year state  pop  debt
# one    2000   aaa  1.0   NaN
# two    2001   bbb  1.1  -1.2
# three  2002   ccc  1.2  -1.7
# four   2003   ddd  1.3  -1.5
# five   2004   eee  1.4   NaN

# 为不存在的列赋值会创建一个新列。
# 关键字del用于删除列
frame3['hello'] = 'hello'
print(frame3)
#        year state  pop  debt  hello
# one    2000   aaa  1.0   NaN  hello
# two    2001   bbb  1.1  -1.2  hello
# three  2002   ccc  1.2  -1.7  hello
# four   2003   ddd  1.3  -1.5  hello
# five   2004   eee  1.4   NaN  hello

del frame3['hello']
print(frame3)
#        year state  pop  debt
# one    2000   aaa  1.0   NaN
# two    2001   bbb  1.1  -1.2
# three  2002   ccc  1.2  -1.7
# four   2003   ddd  1.3  -1.5
# five   2004   eee  1.4   NaN

# 另一种常见的数据形式是嵌套字典
# 外层的字典的键作为列，内层键作为行
pop = {'Nevada': {2001: 2.4, 2002: 2.9}, 'Ohio': {2000: 1.5, 2001: 1.7, 2002: 1.8}}
frame4 = DataFrame(pop)
print(frame4)
#       Nevada  Ohio
# 2000     NaN   1.5
# 2001     2.4   1.7
# 2002     2.9   1.8

# 也可以对结果进行转置
print(frame4.T)
#         2000  2001  2002
# Nevada   NaN   2.4   2.9
# Ohio     1.5   1.7   1.8

# 内层字典的键会被合并、排序以形成最终的索引。如果显式指定了索引，则不会这样
print(DataFrame(pop, index=[2001, 2002, 2003]))
#       Nevada  Ohio
# 2001     2.4   1.7
# 2002     2.9   1.8
# 2003     NaN   NaN


