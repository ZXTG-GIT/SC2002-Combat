package domain.item;

public interface Item {
    String getName();
    void use(Combatant user);
}
