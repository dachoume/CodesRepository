import numpy as np
import matplotlib.pyplot as plt


def mds(D, q):
    D = np.asarray(D)
    DSquare = D ** 2
    totalMean = np.mean(DSquare)
    columnMean = np.mean(DSquare, axis=0)
    rowMean = np.mean(DSquare, axis=1)
    B = np.zeros(DSquare.shape)
    for i in range(B.shape[0]):
        for j in range(B.shape[1]):
            # 这里是求降维后的内积矩阵B
            B[i][j] = -0.5 * (DSquare[i][j] - rowMean[i] - columnMean[j] + totalMean)
    # 分别对应B的特征值和特征向量
    eigVal, eigVec = np.linalg.eig(B)
    # q为参数，对应降维后的维数
    X = np.dot(eigVec[:, :q], np.sqrt(np.diag(eigVal[:q])))

    return X


D = [[0, 587, 1212, 701, 1936, 604, 748, 2139, 2182, 543],
     [587, 0, 920, 940, 1745, 1188, 713, 1858, 1737, 597],
     [1212, 920, 0, 879, 831, 1726, 1631, 949, 1021, 1494],
     [701, 940, 879, 0, 1374, 968, 1420, 1645, 1891, 1220],
     [1936, 1745, 831, 1374, 0, 2339, 2451, 347, 959, 2300],
     [604, 1188, 1726, 968, 2339, 0, 1092, 2594, 2734, 923],
     [748, 713, 1631, 1420, 2451, 1092, 0, 2571, 2408, 205],
     [2139, 1858, 949, 1645, 347, 2594, 2571, 0, 678, 2442],
     [2182, 1737, 1021, 1891, 959, 2734, 2408, 678, 0, 2329],
     [543, 597, 1494, 1220, 2300, 923, 205, 2442, 2329, 0]]

label = ['Atlanta', 'Chicago', 'Denver', 'Houston', 'Los Angeles', 'Miami', 'New York', 'San Francisco', 'Seattle',
         'Washington, DC']
X = mds(D, 2)
plt.plot(X[:, 0], X[:, 1], 'o')
for i in range(X.shape[0]):
    plt.text(X[i, 0] + 25, X[i, 1] - 15, label[i])
plt.show()
