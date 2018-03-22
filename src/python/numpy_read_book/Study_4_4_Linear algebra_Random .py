import numpy as np
from numpy.linalg import inv, qr

# 线性代数

# 矩阵乘法
# x.dot(y)  相当于np.dot(x,y)

# numpy.linalg中有一组标准的矩阵分解运算以及诸如求逆和行列式之类的东西

X = np.random.rand(4, 4)
mat = X.T.dot(X)
print(inv(mat))

# 常用的numpy.linalg函数
# diag   以一维数组的形式返回方阵的对角线（或非对角线）元素，或将一维数组转换为方阵（对角线元素为0）
# dot    矩阵乘法
# trace  计算对角线元素的和
# det    计算矩阵行列式
# eig    计算方阵的本征值和本征向量
# inv    计算方阵的逆
# pinv   计算矩阵的Moore—Penrose伪逆
# qr     计算QR分解
# svd    计算奇异分解（SVD）
# solve  解线性方程组Ax=b，其中A为一个方阵
# Istsq  计算Ax=b的最小二乘解


# 随机数生成

# seed          确定随机数生成器的种子
# permutation   返回一个序列的随机排列或返回一个随机排列的范围
# shuffle       对一个序列就地随机排列
# rand          产生均匀分布的样本值
# randint       从给定的上下限范围内随机选取整数
# randn         产生正态分布（平均值为0，标准差为1）的样本值
# binomial      产生二项分布的样本值
# normal        产生正态（高斯）分布的样本值
# beta          产生Beta分布的样本值
# chisquare     产生卡方分布的样本值
# gamma         产生Gamma分布的样本值
# uniform       产生在[0,1)中均匀分布的样本值
