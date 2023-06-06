import java.util.LinkedHashSet;
  default Set<String> getGroupsSet(String user) throws IOException {
    //Override to form the set directly to avoid another conversion
    return new LinkedHashSet<>(getGroups(user));
  }
