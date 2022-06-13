import { Model, Expression, BinaryExpression, NumberLiteral } from '../language-server/generated/ast';

export function evaluateExpression(model: Model): Array<number> {
    return model.expressions.map(expression => evalExpression(expression));
}

function evalExpression(expression: Expression) : number {
    if (!('left' in expression)) {
        return evalNumberLiteral(expression);
    } else {
        return evalBinaryExpression(expression);
    }
}

function evalNumberLiteral(e : NumberLiteral) : number {
    switch (e.sign) {
        case '-':
            return -(e.value);
        case '+':
        default:
            return e.value;
    }
}

function evalBinaryExpression(e : BinaryExpression) : number {
    switch (e.operator) {
        case '+':
            return evalExpression(e.left) + evalExpression(e.right);
        case '-':
            return evalExpression(e.left) - evalExpression(e.right);
        case '*':
            return evalExpression(e.left) * evalExpression(e.right);
        case '/':
            return evalExpression(e.left) / evalExpression(e.right);
        default:
            throw `Unknown operator error. operator: ${e.operator}`;
    }
}
