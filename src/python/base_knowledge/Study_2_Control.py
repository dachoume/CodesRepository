# if
x = 7
if x < 0:
    print('负数')
elif x == 0:
    print('零')
else:
    print('正数')

# 对于用and或or组成的复合条件，各条件是按从左到右的顺序求值的，而且是短路型（如or左右的两个条件中有一个为true整个条件都为true）

# =================================================================================
# for

# continue关键字用于使for循环提前进入下一次迭代（即跳过代码块的剩余部分）
a = [1, 2, 3, None, 4]
sum_1 = 0
for value in a:
    if value is None:
        continue  # 功能是对列表中的数求和并跳过None值
    sum_1 += value
print(sum_1)

# break关键字用于使for循环完全退出
b = [1, 2, 3, 5, 4]
sum_2 = 0
for value in b:
    if value == 5:
        break  # 遇到5直接推出整个循环
    sum_2 += value
print(sum_2)

# =================================================================================
# while

c = 10

while c > 0:
    c -= 1
print(c)

# =================================================================================
# pass

# 在开发一个新功能时，常常会将pass用作代码中的占位符
d = 7
if d < 0:
    print('负数')
elif d == 0:
    pass
else:
    print('正数')


# =================================================================================
# 异常处理

def attempt_float(x):
    try:
        return float(x)
    except:  # 如果except后面不加上具体的错误名称的话意味者可以处理发生的全部错误
        return x


print(attempt_float('some'))
print(attempt_float('1.234'))


def attempt_float_2(x):
    try:
        return float(x)
    except ValueError:  # 只可以处理具体类型的错误，也可以写成（TypeError,ValueError）能处理多个错误
        return x


# 如果想要一段代码不管try是否成功都执行用finally
# 如果想让代码只在try成功时执行用else
# try:
#     pass
# except:
#     pass
# else:
#     pass
# finally:
#     pass

# =================================================================================
# 三元表达式

# value = true-expr if condition else false-expr
# 其中的true-expr和false-expr可以是任何的Python表达式
e = -5
print('负数' if e < 0 else '正数')
