package com.example.a325.datas;

public class AllCourseData {

    private int id;
    private String name;    //列表项名称
    private String pic;     //列表项图片
    private int numbers;    //列表项之下的课程数
    private boolean isTitle = false;    //列表项是否为title

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int number) {
        this.numbers = number;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    @Override
    public String toString() {
        return "ClassifyData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", numbers=" + numbers +
                ", isTitle=" + isTitle +
                '}';
    }
}
