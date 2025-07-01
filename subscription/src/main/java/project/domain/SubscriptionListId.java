package project.domain;

import java.io.Serializable;
import java.util.Objects;

public class SubscriptionListId implements Serializable {
    private Long userId;
    private Long bookId;

    public SubscriptionListId() {}

    public SubscriptionListId(Long userId, Long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubscriptionListId)) return false;
        SubscriptionListId that = (SubscriptionListId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookId);
    }
}
