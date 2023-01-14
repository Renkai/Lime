# http://bbs.pinyin.thunisoft.com/forum.php?mod=viewthread&tid=28002&highlight=%D7%D4%C8%BB%BF%EC%CA%D6

def extract():
    loge = open("自然快手.txt", "w")
    count = 0
    with open("蓝天快手单字.ini", "r", encoding="utf-16le") as f:
        lines = f.readlines()
        for line in lines:
            count += 1
            print(f"len {len(line)}")
            line = line.strip()
            print(f"len2 {len(line)}")
            if len(line) == 0:
                continue

            code = line[2:4].strip()
            char = line[6:].strip()
            print(f"line: {line}")
            print(char)
            print(code)
            loge.write(f"{char}\t{code}\n")


if __name__ == '__main__':
    extract()
