import { /*ValidationAcceptor,*/ ValidationCheck, ValidationRegistry } from 'langium';
import { ArithmeticsAstType } from './generated/ast';
import type { ArithmeticsServices } from './arithmetics-module';

/**
 * Map AST node types to validation checks.
 */
type ArithmeticsChecks = { [type in ArithmeticsAstType]?: ValidationCheck | ValidationCheck[] }

/**
 * Registry for validation checks.
 */
export class ArithmeticsValidationRegistry extends ValidationRegistry {
    constructor(services: ArithmeticsServices) {
        super(services);
        const validator = services.validation.ArithmeticsValidator;
        const checks: ArithmeticsChecks = {
        };
        this.register(checks, validator);
    }
}

/**
 * Implementation of custom validations.
 */
export class ArithmeticsValidator {

//    checkPersonStartsWithCapital(person: Person, accept: ValidationAcceptor): void {
//        if (person.name) {
//            const firstChar = person.name.substring(0, 1);
//            if (firstChar.toUpperCase() !== firstChar) {
//                accept('warning', 'Person name should start with a capital.', { node: person, property: 'name' });
//            }
//        }
//    }

}
