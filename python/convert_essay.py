def cc_essay():
    import opencc
    converter = opencc.OpenCC('t2s.json')

    out_file = open("../luna_zrku/simp_dicts/essay_s.txt", "w")

    for line in open("../luna_zrku/dicts/essay.txt"):
        # print("line ", line)
        [word, freq] = line.split("\t")
        word = converter.convert(word)
        out_file.write(f"{word}\t{freq}")


if __name__ == '__main__':
    cc_essay()
