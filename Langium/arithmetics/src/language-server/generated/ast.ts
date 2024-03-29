/******************************************************************************
 * This file was generated by langium-cli 0.3.0.
 * DO NOT EDIT MANUALLY!
 ******************************************************************************/

/* eslint-disable @typescript-eslint/array-type */
/* eslint-disable @typescript-eslint/no-empty-interface */
import { AstNode, AstReflection, isAstNode } from 'langium';

export type Expression = BinaryExpression | NumberLiteral;

export const Expression = 'Expression';

export function isExpression(item: unknown): item is Expression {
    return reflection.isInstance(item, Expression);
}

export interface BinaryExpression extends AstNode {
    readonly $container: BinaryExpression | Model;
    left: Expression
    operator: '*' | '/' | '+' | '-'
    right: Expression
}

export const BinaryExpression = 'BinaryExpression';

export function isBinaryExpression(item: unknown): item is BinaryExpression {
    return reflection.isInstance(item, BinaryExpression);
}

export interface Model extends AstNode {
    expressions: Array<Expression>
}

export const Model = 'Model';

export function isModel(item: unknown): item is Model {
    return reflection.isInstance(item, Model);
}

export interface NumberLiteral extends AstNode {
    readonly $container: BinaryExpression | Model;
    sign: '+' | '-'
    value: number
}

export const NumberLiteral = 'NumberLiteral';

export function isNumberLiteral(item: unknown): item is NumberLiteral {
    return reflection.isInstance(item, NumberLiteral);
}

export type ArithmeticsAstType = 'BinaryExpression' | 'Expression' | 'Model' | 'NumberLiteral';

export type ArithmeticsAstReference = never;

export class ArithmeticsAstReflection implements AstReflection {

    getAllTypes(): string[] {
        return ['BinaryExpression', 'Expression', 'Model', 'NumberLiteral'];
    }

    isInstance(node: unknown, type: string): boolean {
        return isAstNode(node) && this.isSubtype(node.$type, type);
    }

    isSubtype(subtype: string, supertype: string): boolean {
        if (subtype === supertype) {
            return true;
        }
        switch (subtype) {
            case BinaryExpression:
            case NumberLiteral: {
                return this.isSubtype(Expression, supertype);
            }
            default: {
                return false;
            }
        }
    }

    getReferenceType(referenceId: ArithmeticsAstReference): string {
        switch (referenceId) {
            default: {
                throw new Error(`${referenceId} is not a valid reference id.`);
            }
        }
    }
}

export const reflection = new ArithmeticsAstReflection();
