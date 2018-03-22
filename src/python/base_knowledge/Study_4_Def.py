# 函数是用def关键字声明的，并使用return关键字返回，同时拥有多条return语句也是可以的
# 如果到达函数末尾时没有遇到任何一条return语句，则返回None


def my_function1(x, y, z=1.5):
    if z > 1:
        return z * (x + y)
    else:
        return z / (x + y)


# 函数可以有一些位置参数和一些关键字参数，关键字参数通常用于指定默认值或可选参数。
# 函数参数的主要限制在于：关键字参数必须位于位置参数（如果有的话）之后。

# 可以在函数中对全局变量进行赋值操作，但是那些变量必须用global关键字声明成全局的
a = None


def my_functino2():
    global a
    a = []


my_functino2()
print(a)  # []


# 不要频繁使用global关键字，如果用了需要则需要使用类

# Python可以返回多个值，如return a,b,c，也可以返回字典

# lambda(匿名)函数
def short_function(x):
    return x * 2


equiv_anon = lambda x: x * 2

print(equiv_anon(5))
