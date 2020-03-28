package software.simple.solutions.framework.core.repository;

public interface IGenderRepository extends IGenericRepository {

	Boolean isNameUnique(Long id, String name);

}
