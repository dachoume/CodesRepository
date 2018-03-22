fr=open('G:/MyPythonCode/test_files/test_read_file.txt')
while 1:
    line = fr.readline()
    if not line:
        break
    s =line.strip().split(' ')[0]
    print(s)
