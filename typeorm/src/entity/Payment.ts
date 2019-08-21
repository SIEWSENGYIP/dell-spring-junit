import {Entity, PrimaryGeneratedColumn, Column, CreateDateColumn, UpdateDateColumn, ManyToOne, JoinColumn} from "typeorm";
import { Order } from "./Order";

@Entity("payments")
export class Payment {

    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    amount: number;

    @Column()
    paid: boolean;

    @Column()
    refunded: boolean;

    @ManyToOne(type => Order)
    @JoinColumn({name: "order_id"})
    order;
}
