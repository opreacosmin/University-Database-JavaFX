package map.repo;
import java.util.ArrayList;
import java.util.List;

public abstract class InMemoryRepo<T> implements ICrudRepo<T> {
    protected List<T> repoList;

    public InMemoryRepo() {
        this.repoList = new ArrayList<>();

    }

    @Override
    public List<T> getAll() {
        return this.repoList;
    }

}
