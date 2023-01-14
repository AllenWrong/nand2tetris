package com.module;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rekord
 * @date 2023/1/14 16:09
 */
public class AutoTest {
    List<String> files = null;

    public AutoTest() {
        this.init();
    }

    public void init() {
        this.files = new ArrayList<String>();
        files.add("./add/Add");
        files.add("./max/Max");
        files.add("./pong/Pong");
        files.add("./rect/Rect");
    }

    // start test
    public void start() throws Exception {
        for (String file : files) {
            Assembler assembler = new Assembler(new File(file + ".asm"));
            assembler.firstScanFile();
            assembler.secondScanFile();

            if (!compare(file + ".hack", file + ".cmp")) {
                System.out.println("\nComparison failed.");
                return;
            }

            Thread.sleep(2000);
        }
        System.out.println("\nComparison succeeded.");
    }

    // verification results
    public boolean compare(String hackFile, String cmpFile) throws IOException {
        List<String> list1 =  Files.readAllLines(Paths.get(hackFile));
        List<String> list2 =  Files.readAllLines(Paths.get(cmpFile));

        List<String> finalList = list2.stream().filter(line ->
                list1.stream().filter(line2 -> line2.equals(line)).count() == 0
        ).collect(Collectors.toList());
        if (finalList.size() == 0) {
            return true;
        } else {
            finalList.forEach(one -> System.out.println(one));
        }
        return false;
    }

}