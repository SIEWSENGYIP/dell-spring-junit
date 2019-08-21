import {Entity, PrimaryGeneratedColumn, Column, CreateDateColumn, UpdateDateColumn, OneToMany} from "typeorm";
import { LineItem } from "./LineItem";

@Entity("products")
export class Product {

    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    name: string;

    @Column()
    price: number;

    @OneToMany(type => LineItem, lineItem => lineItem.product)
    lineItems: LineItem[];
}
