import com.stream.api.Stream;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<Integer> list = List.of(1,3,5,7,9,2,4,6,8,10,12,214,16,18,20);
        Stream<Integer> stream = new Stream<>(list);
        Stream<String> stream1 = stream.filter(i->i>3)
                .filter(i->i%2 == 0)
                .map(String::valueOf)
                .filter(i -> i.length() >= 2);
        System.out.println("Performing lazy evaluation...");
        stream1.forEach(System.out::println);
//        stream.forEach(System.out::println);
    }
}