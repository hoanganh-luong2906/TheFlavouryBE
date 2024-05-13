package project.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "token_types")
public class TokenType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_type_id")
    private int id;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "tokenType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Token> tokens;
}
