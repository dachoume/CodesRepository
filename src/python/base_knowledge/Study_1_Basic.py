# 变量和按引用传递
a = [1, 2, 3]
b = a
a.append(4)
print(b)  # [1, 2, 3, 4]
# Python被称为是按引用传递的，a和b都指向同一个对象即原始列表[1,2,3]

# =================================================================================
# 通过isinstance函数可以检查一个对象是否是某个特定类型的实例
c = 5
print(isinstance(c, int))


# 结果是true

# =================================================================================
# 验证是不是可迭代类型

def isiterable(obj):
    try:
        iter(obj)
        return True
    except TypeError:  # 不可迭代
        return False


print(isiterable('a'))  # 结果是True
print(isiterable(5))  # 结果是False

# 先检查是不是列表（数组），如果不是就把它变成可以迭代的
# 写法不是很懂
d = 5
if not isinstance(d, list) and isiterable(d):
    x = list(d)

# =================================================================================
# 比较运算符 == 和is、is not
e = [1, 2, 3]

f = e

g = list(e)  # list函数始终会创建新列表
print(g)  # [1,2,3]

print(e is f, e is g, e == g)  # 感觉类似于java中的==和equals

# is 和is not 常常用来判断变量是否为None，因为None的实例只有一个

# =================================================================================
# 大部分的Python对象是可变的，如列表、字典、Numpy数组以及大部分用户自定义类型，而其他的（如字符串和元组等）则是不可变的
# 不可变的意思是不能修改原内存块的数据，即使修改成功了也只是创建了一个新对象并将其引用赋值给原变量而已（这种行为叫做副作用）

# =================================================================================
print(3 / 2)  # 1.5
print(3 // 2)  # 1

# =================================================================================
# 字符串
# 使用str函数可以将对象转为字符串
h = 3.5
print(isinstance(str(h), str))  # True

i = 'Python'
print(list(i))  # list函数将字符串变为了一串字符

# 字符串格式化
# %s表示字符串，%d表示一个整数，%.2f表示一个带有2位小数的数字

template = '%.2f %s are worth $%d'
print(template % (4.456, 'hello', 1))
# =================================================================================
# 布尔值 True、False
# 空序列（列表，字典，元组）用于控制流（if、elif、else）就会被当成False处理
print(bool([]), bool([1, 2, 3]))
print(bool(''), bool('hello'))

# =================================================================================
# 时间日期
from datetime import datetime, date, time

dt = datetime(2017, 9, 30, 16, 30, 45)
print(dt.date())
print(dt.time())

# strftime方法用于将datetime格式化为字符串
print(dt.strftime('%m/%d/%Y %H:%M'))

# strptime函数将字符串解析为datetime对象
print(datetime.strptime('20091031', '%Y%m%d'))

# 对datetime中的字段进行替换,并产生一个新对象
print(dt.replace(minute=0))

# 两个datetime对象的差会产生一个datetime.timedelta类型
# 将一个timedelta加到一个datetime上会产生一个新的datetime
