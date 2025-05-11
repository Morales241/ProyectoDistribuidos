package mappers;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Convertidor<T, U>  {

    private final Function<T, U> fromDto;
    private final Function<U, T> fromEntity;

    public Convertidor(final Function<T, U> fromDto, final Function<U, T> fromEntity) {
        this.fromDto = fromDto;
        this.fromEntity = fromEntity;
    }

    public final T convertFromEntity(U u) {

        return fromEntity.apply(u);
    }

    public final U convertFromDto(T t) {
        return fromDto.apply(t);
    }

    public final List<U> createFromDtos(List<T> dtos) {
        return dtos.stream().map(fromDto).collect(Collectors.toList());
    }

    public final List<T> createFromEntities(Collection<U> entities) {
        return entities.stream().map(fromEntity).collect(Collectors.toList());
    }
}
