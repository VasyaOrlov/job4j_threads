package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        Account rsl = accounts.putIfAbsent(account.getId(), account);
        return rsl == null;
    }

    public synchronized boolean update(Account account) {
        Account rsl = accounts.replace(account.getId(), account);
        return rsl != null;
    }

    public synchronized boolean delete(int id) {
        Account rsl = accounts.remove(id);
        return rsl != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Account first = accounts.get(fromId);
        Account second = accounts.get(toId);
        if (first != null
                && second != null
                && first.amount() >= amount) {
            first.setAmount(first.amount() - amount);
            second.setAmount(second.amount() + amount);
            return true;
        }
        return false;
    }
}