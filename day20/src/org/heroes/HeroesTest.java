package org.heroes;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HeroesTest {
    //需求：
    // 1. 找到武将中武力前三的hero对象, 提示流也可以排序
    // 2. 按出生地分组
    // 3. 找出寿命前三的武将
    // 4. 女性寿命最高的
    // 5. 找出武力排名前三  100, 99, 97 97 ==> 4个人 吕布", "张飞", "关羽", "马超
    // 6. 按各个年龄段分组： 0~20, 21~40, 41~60, 60以上
    // 7. 按武力段分组： >=90， 80~89, 70~79, <70
    // 8. 按出生地分组后，统计各组人数
    public static void main(String[] args) throws IOException {
        Stream<String> lines = Files.lines(Paths.get("heroes.txt"), Charset.forName("utf-8"));
        //遍历一下
        //lines.forEach(str -> System.out.println(str));
        //创建一个集合装武将对象
        ArrayList<Hero> herosList = new ArrayList<>();
        //将每个武将转换为对象并装进集合中
        lines.forEach(ele ->{
            String[] split = ele.split("\t");
            Hero hero = new Hero(Integer.parseInt(split[0]), split[1], split[2], split[3], Integer.parseInt(split[4]), Integer.parseInt(split[5]), Integer.parseInt(split[6]));
            herosList.add(hero);
        });
        //System.out.println(herosList);
        //1. 找到武将中武力前三的hero对象, 提示流也可以排序
        List<Hero> mostLevel = herosList.stream().sorted((a, b) -> b.getLevel() - a.getLevel()).limit(3).collect(Collectors.toList());
        System.out.println("武力值前三的英雄");
        System.out.println(mostLevel);
        // 2. 按出生地分组
        Map<String, List<Hero>> groupCity = herosList.stream().collect(Collectors.groupingBy(a -> a.getCity()));
        System.out.println("按出生地分组");
        System.out.println(groupCity);
        for (String string : groupCity.keySet()) { //统计每组多少人
            int size = groupCity.get(string).size();
            System.out.print(string);
            System.out.print(size+"\t");
        }
        System.out.println();
        // 3. 找出寿命前三的武将
        List<Hero> mostLife = herosList.stream().sorted((a, b) -> b.getDeath() - b.getBirth() - a.getDeath() + a.getBirth()).limit(3).collect(Collectors.toList());
        System.out.println("寿命前三的武将");
        System.out.println(mostLife);
        // 4. 女性寿命最高的
        Optional<Hero> max = herosList.stream().filter(a -> a.getSex().equals("女")).max((a, b) -> b.getDeath() - b.getBirth() - a.getDeath() + a.getBirth());
        System.out.println("寿命最高的女");
        System.out.println(max);
        // 5. 找出武力排名前三  100, 99, 97 97 ==> 4个人 吕布", "张飞", "关羽", "马超
//        List<Hero> wuliqiansan = herosList.stream().sorted((a, b) -> b.getLevel() - a.getLevel()).limit(3).collect(Collectors.toList());
        TreeSet<Hero> wuliqiansan = new TreeSet<>((a, b) -> b.getLevel() - a.getLevel());
        mostLevel.forEach(a -> wuliqiansan.add(a));
        //System.out.println(wuliqiansan);
        //System.out.println(wuliqiansan.last());
        for (Hero hero : herosList) {
            for (Hero hero1 : wuliqiansan) {
                if (hero.getLevel()==hero1.getLevel()){
                    //System.out.println(hero);
                    wuliqiansan.add(hero);
                }
            }
        }
        System.out.println("武力值前三名");
        System.out.println(wuliqiansan);
        // 6. 按各个年龄段分组： 0~20, 21~40, 41~60, 60以上
        Map<Integer, List<Hero>> nianling = herosList.stream().collect(Collectors.groupingBy(a -> (a.getDeath()-a.getBirth())/20));
        System.out.println("年龄分段");
        HashMap<String, List<Hero>> groupOfAge = new HashMap<>();
        Set<Integer> integers = nianling.keySet();
        groupOfAge.put("0~20",nianling.get(0));
        groupOfAge.put("21~40",nianling.get(0));
        groupOfAge.put("41~60",nianling.get(0));
        groupOfAge.put("60以上",nianling.get(0));


    }
}
