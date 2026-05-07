package entity.copy;
import jakarta.persistence.Entity;

@Entity
public class User {
	@Id
	private Long id;
	private String name;
}
