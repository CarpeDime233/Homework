package org.heroes;

public class Hero {
    public int id;
    public String name;
    public String city;
    public String sex;
    public int birth;
    public int death;
    public int level;

    public Hero(int id, String name, String city, String sex, int birth, int death, int level) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.sex = sex;
        this.birth = birth;
        this.death = death;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getSex() {
        return sex;
    }

    public int getBirth() {
        return birth;
    }

    public int getDeath() {
        return death;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", sex='" + sex + '\'' +
                ", birth=" + birth +
                ", death=" + death +
                ", level=" + level +
                '}';
    }
}
