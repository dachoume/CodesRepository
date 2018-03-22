# 元组（圆括号）

# 元组是一种一维的、定长的、不可变的Python对象序列，最简单的创建方式是一组以逗号隔开的值
tup_1 = 1, 2, 3
print(tup_1)  # (1, 2, 3)

# 复杂的元组
tup_2 = (1, 2, 3), (4, 5)
print(tup_2)  # ((1, 2, 3), (4, 5))

# 通过调用tuple，任何序列或迭代器都可以被转换为元组
print(tuple([1, 2, 3]))  # (1, 2, 3)
print(tuple('string'))  # ('s', 't', 'r', 'i', 'n', 'g')

# 存储在元组中的对象本身可能是可变的（如对象是列表等），但一旦创建完毕，存放在各个插槽中的对象就不能再被修改了（如tup_2[2]=4是错误的）
tup_3 = (1, [1, 2, 3], 'string')
tup_3[1].append(4)
print(tup_3)  # (1, [1, 2, 3, 4], 'string')

# 元组可以通过+连接起来产生更长的元组
tup_4 = tup_1 + tup_3
print(tup_4)  # (1, 2, 3, 1, [1, 2, 3, 4], 'string')

# 和列表一样，一个元组乘以一个整数，相当于是连接该元组的多个副本(对象本身是不会被复制的，涉及的只是它们的引用而已)
print(tup_1 * 3)  # (1, 2, 3, 1, 2, 3, 1, 2, 3)

# 元组拆包
a, b, c = tup_1
print(a)  # 1

# 嵌套元组也可以拆包
(d, e, f), (g, h) = tup_2
print(g)  # 4

# 元组方法
# count：计算指定值的出现次数
print((1, 1, 1, 2, 2, 3,).count(1))  # 3

# =================================================================================
# 列表（方括号）

# 和元组相比，列表是变长的，而且内容也是可以修改的。它可以通过方括号或list函数进行定义
list_1 = [1, 2, 3]
print(list_1)  # [1, 2, 3]

print(list('string'))  # ['s', 't', 'r', 'i', 'n', 'g']
# 注意：list(1)这一方法是错误的，因为1不是可以迭代的类型

# 添加和移除元素
# append方法可以将元素添加到列表的末尾，insert可以将元素插入到列表的指定位置
# insert的计算量比append要大
list_1.insert(1, 10)
print(list_1)  # [1, 10, 2, 3]

# insert的逆运算是pop，用于移除并返回指定索引处的元素
list_1.pop()  # 没有参数移除最后一个
print(list_1)  # [1, 10, 2]
list_1.pop(1)
print(list_1)  # [1, 2]

# remove用于等值删除元素，它找到第一个符合要求的值然后将其从列表中删除

# 通过in关键字可以判断列表中是否含有某个值
print(1 in list_1)  # True

# 列表合并
# 和元组一样，+可以将两个列表加起来即可实现合并
# 对于一个已定义的列表，可以用extend方法一次性添加多个元素
list_1.extend([3, 4, 5])
print(list_1)  # [1, 2, 3, 4, 5]

# extend性能比+好
everything = []
for x in [[11], [22], [33]]:
    everything.extend(x)
print(everything)  # [11, 22, 33]

# 排序
# 调用列表的sort方法可以实现就地排序
list_2 = [6, 5, 7, 8, 9]
list_2.sort()
print(list_2)  # [5, 6, 7, 8, 9]

# 内置的bisect模块实现了二分查找以及对有序列表的插入操作
# bisect.bisect可以找出新元素应该被插入到哪个位置才能保持原列表的有序性，
# 而bisect.insort则确实地将新元素插入到那个位置上去
import bisect

list_3 = [1, 2, 2, 3, 4, 5, 6]
print(bisect.bisect(list_3, 4))  # 5
bisect.insort(list_3, 2)
print(list_3)  # [1, 2, 2, 2, 3, 4, 5, 6]

# 切片
# 可以选取序列类型的子集
print(list_3[2:4])  # [2, 2]  左闭右开
print(list_3[:4])  # [1, 2, 2, 2]  默认从头开始

# 可以在第二个冒号后面加上步长
print(list_3[::2])  # [1, 2, 3, 5]

# 在这里使用-1可以实现列表或元组的反序
print(list_3[::-1])  # [6, 5, 4, 3, 2, 2, 2, 1]

# 内置的序列函数
# 1、enumerate
# 可以在迭代时逐个返回序列的（索引，值）元组
# 也可以将序列值映射到其所在位置的字典
list_4 = ['hello', 'hi', 'world']
print(dict((v, i) for (i, v) in enumerate(list_4)))  # {'hello': 0, 'hi': 1, 'world': 2}

# 2、sorted
# 将任何序列返回为一个新的有序列表
print(sorted(list_3))  # [1, 2, 2, 2, 3, 4, 5, 6]

# 3、zip
# zip用于将多个序列中的元素配对，从而产生一个新的元组列表
# zip可以接受任意数量的序列，最终得到的元组数量由最短的序列决定
a = zip(list_3, list_4)
# for x in a:
#     print(x)

# 可以使用unzip进行解压缩

# 4、reversed
# 用于按逆序迭代序列中的元素
list_3.reverse()
print(list_3)  # [6, 5, 4, 3, 2, 2, 2, 1]

# =================================================================================
# 字典

# 创建字典的方式之一是：使用大括号{}并用冒号分隔键和值
d1 = {'a': (1, 2, 3), 'b': [1, 2]}
print(d1)  # {'a': (1, 2, 3), 'b': [1, 2]}

# 插入、设置元素的语法和列表、元组是一样的
d1[7] = 'seven'
print(d1)  # {'a': (1, 2, 3), 'b': [1, 2], 7: 'seven'}

print(d1['b'])  # [1, 2]

# 可以使用in判断字典中是否存在某个键（也有not in）
print(7 in d1)  # True

# 使用del关键字或pop方法（删除指定值之后将其返回）可以删除值
d1[5] = 'five'
d1[6] = 'six'
del d1[5]
print(d1)  # {'a': (1, 2, 3), 'b': [1, 2], 7: 'seven', 6: 'six'}
tmp_d1 = d1.pop(6)
print(tmp_d1)  # six

# keys和values方法分别用于获取键和值的列表(如果使用Python3返回的是迭代器而不是列表)
# 虽然键值对没有特定的顺序，但这两个函数会以相同的顺序输出键和值
print(d1.keys())  # dict_keys(['a', 'b', 7])
print(d1.values())  # dict_values([(1, 2, 3), [1, 2], 'seven'])

# 使用update方法，一个字典可以被合并到另一个字典中去
d1.update({'b': 1, 'c': 2})
print(d1)  # {'a': (1, 2, 3), 'b': 1, 7: 'seven', 'c': 2}

# 好神奇
word = 'string'
print(word[0])  # s

# 字典的get与默认值
print(d1.get('d', '默认值'))  # 默认值

# 字典的值可以是任何Python对象，但是字典的键必须是不可变对象
# 如果要将列表当作键，最简单的办法是将其转换成元组
# =================================================================================
# 集合

# 集合是由唯一元素组成的无序集
# 集合的创建方式有二：set函数或使用大括号{}
print(set([1, 2, 3]))  # {1, 2, 3}  不建议使用
print({1, 2, 3, 3, 4, 5, 5})  # {1, 2, 3, 4, 5}

# Python的集合运算

# a.add(x)                    将元素x添加到集合a
# a.remove(x)                 将元素x从集合a中删除
# a.union(b)                  a和b全部的唯一元素（a|b）
# a.intersection(b)           a和b都有的元素（a&b）
# a.difference(b)             a中不属于b的元素（a-b）
# a.symmetric_difference(b)   a或b中不同时属于a和b的元素（a^b）
# a.issubset(b)               如果a的全部元素都包含于b，则为True
# a.issuperset(b)             如果b的全部元素都包含于a，则为True
# a.isdisjoint(b)             如果a和b没有公共元素，则为True
# =================================================================================
# 列表、集合、字典的推导式

# 1、列表推导式
# [expr for val in collection if condition]

# 相当于下面这段for循环
# result=[]
# for val in collection:
#     if condition:
#         result.append(expr)

# 过滤条件可以省略，只留下表达式

strings = ['a', 'as', 'bat', 'car', 'dove', 'python']
l_1 = [x.upper() for x in strings if len(x) > 2]
print(l_1)  # ['BAT', 'CAR', 'DOVE', 'PYTHON']

# 2、集合推导式
# 和列表推导式非常相似，只是用花括号代替了方括号
# {expr for value in collection if condition}

s_1 = {len(x) for x in strings}
print(s_1)  # {1, 2, 3, 4, 6}

# 3、字典推导式
# {key-expr : value-expr for value in collection if condition}
d_1 = {val: index for index, val in enumerate(strings)}  # 如果写成(index,val)就不会想那么长时间了
print(d_1)  # {'a': 0, 'as': 1, 'bat': 2, 'car': 3, 'dove': 4, 'python': 5}

# 嵌套列表推导式

some_tuples = [(1, 2, 3), (4, 5), (6, 7, 8, 9)]
flattened = [x for tup in some_tuples for x in tup]
print(flattened)  # [1, 2, 3, 4, 5, 6, 7, 8, 9]
