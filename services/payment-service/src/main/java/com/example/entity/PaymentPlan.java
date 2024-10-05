package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.Year;
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
public class PaymentPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_plan_id")
    private Long paymentPlanId;

    @Column(name = "annual_cost", nullable = false)
    private Double annualCost;

    @Column(name = "number_of_phases", nullable = false)
    private Integer numberOfPhases;

    @OneToMany(mappedBy = "paymentPlan", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PaymentPhase> paymentPhases;

    @Column(name = "scholarship_year")
    private Integer scholarshipYear;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        PaymentPlan that = (PaymentPlan) o;
        return getPaymentPlanId() != null && Objects.equals(getPaymentPlanId(), that.getPaymentPlanId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
