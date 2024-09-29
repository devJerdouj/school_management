package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PaymentPhase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payment_plan_id", nullable = false)
    private PaymentPlan paymentPlan;

    @Column
    private Long studentId;

    @Column(name = "amount_due", nullable = false)
    private Double amountDue;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid = false;

    @Column(name = "remaining_amount", nullable = false)
    private Double remainingAmount;

    @Column(name = "payment_date", nullable = true)
    private LocalDate paymentDate;

    @Column
    @OneToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Payment> payments ;

    public void markAsPaid(){
        this.setRemainingAmount(0.0);
        this.setPaymentDate(LocalDate.now());
        this.setIsPaid(true);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        PaymentPhase that = (PaymentPhase) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
