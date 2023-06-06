import java.util.Arrays;
import java.util.Comparator;
      Field[] sortedFields = clazz.getDeclaredFields();
      Arrays.sort(sortedFields, new Comparator<Field>() {
        public int compare(Field a, Field b) {
          return a.getName().compareTo(b.getName());
        }
      });
      for (Field field : sortedFields) {
