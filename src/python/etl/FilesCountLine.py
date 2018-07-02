import os

"""
用来判断一个目录下的文件的行数的（没有过滤掉中间空行的功能）
"""


def countLine(filepath):
    print('文件路径：', filepath)
    count = 0
    list_linesize = []
    for index, line in enumerate(open(filepath, 'r')):
        # print(line)
        list_linesize.append(len(line.strip()))  # strip() 方法用于移除字符串头尾指定的字符（默认为空格或换行符）或字符序列。
        count += 1
    # list_linesize.sort(reverse=True)
    print('每行的字符数：', list_linesize)
    return count


def getDirCountLineList(rootdir):
    list_files = os.listdir(rootdir)  # 列出文件夹下所有的目录与文件
    list_linecount = []
    for i in range(0, len(list_files)):
        path = os.path.join(rootdir, list_files[i])
        if os.path.isfile(path):
            lc = countLine(path)
            # print(path, lc)  # 打印每个文件行数的
            print('行数：', lc)
            list_linecount.append(lc)
    list_linecount.sort(reverse=True)  # 行数倒序
    print('所有文件行数的倒序：')
    return list_linecount


if __name__ == "__main__":
    rootdir = '/Users/dxdou/Downloads/traffic_data'  # 目录名
    print(getDirCountLineList(rootdir))
