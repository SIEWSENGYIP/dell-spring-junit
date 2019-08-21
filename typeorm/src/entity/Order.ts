import {Entity, PrimaryGeneratedColumn, Column, CreateDateColumn, UpdateDateColumn, OneToMany} from "typeorm";
import { LineItem } from "./LineItem";
import { Payment } from "./Payment";

@Entity("orders")
export class Order {

    @PrimaryGeneratedColumn()
    id: number;

    @OneToMany(type => LineItem, lineItem => lineItem.order)
    lineItems: LineItem[];

    @OneToMany(type => Payment, payment => payment.order)
    payments: Payment[];
}
