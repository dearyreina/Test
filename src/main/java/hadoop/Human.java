package hadoop;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class Human implements WritableComparable<Human> {
    private int gender;
    private int age;

    public Human(){}

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(Human o) {
        int result = Integer.valueOf(this.gender).compareTo(Integer.valueOf(o.gender));
        if(result == 0){
            result = Integer.valueOf(this.age).compareTo(Integer.valueOf(o.age));
        }
        return result;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(gender);
        dataOutput.writeInt(age);

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        gender = dataInput.readInt();
        age = dataInput.readInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        //return gender == human.gender;
        return gender == human.gender &&
                age == human.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gender,age);
    }
}
