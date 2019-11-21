package com.catt.common.util.template.beetl;

import org.beetl.core.Context;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.om.ObjectAA;
import org.beetl.core.statement.VarRef;

/**
 * <pre>
 *
 * @author : Zhang zhongtao
 * @version : Ver 1.0
 * </pre>
 */
public class ExtVarRef extends VarRef {
    /**
     * 特殊标识
     */
    public static final String ROOT_FLAG = "$$root";

    String attr;

    public ExtVarRef(VarRef ref) {
        super(ref.attributes, ref.hasSafe, ref.safe, ref.token, ref.token);
        this.varIndex = ref.varIndex;
        attr = getAttrNameIfRoot(ref.token.text);


    }

    public Object evaluate(Context ctx) {

        Object value = ctx.vars[varIndex];
        if (value == Context.NOT_EXIST_OBJECT) {
            // check root
            Object o = ctx.getGlobal(ROOT_FLAG);
            if (o == null) {
                return super.evaluate(ctx);
            } else {
                try {

                    Object realValue = ObjectAA.defaultObjectAA().value(o, attr);
                    ctx.vars[varIndex] = realValue;

                } catch (BeetlException e) {
                    BeetlException ex = new BeetlException(BeetlException.VAR_NOT_DEFINED, e.getMessage());
                    ex.pushToken(this.token);
                    throw ex;
                }
                return super.evaluate(ctx);

            }
        }
        return super.evaluate(ctx);
    }

    private String getAttrNameIfRoot(String name) {
        //todo []
        int index = name.indexOf('.');
        if (index != -1) {
            return name.substring(0, index);
        } else {
            return name;
        }
    }
}
