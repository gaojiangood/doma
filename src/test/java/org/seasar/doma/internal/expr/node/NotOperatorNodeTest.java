package org.seasar.doma.internal.expr.node;

import junit.framework.TestCase;
import org.seasar.doma.internal.expr.ExpressionEvaluator;

public class NotOperatorNodeTest extends TestCase {

  protected ExpressionLocation location = new ExpressionLocation("", 0);

  protected LiteralNode trueLiteral = new LiteralNode(location, "true", true, boolean.class);

  protected LiteralNode falseLiteral = new LiteralNode(location, "false", false, boolean.class);

  protected LiteralNode nullLiteral = new LiteralNode(location, "null", null, Object.class);

  public void test_true() throws Exception {
    var node = new NotOperatorNode(location, "!");
    node.setNode(trueLiteral);
    var evaluator = new ExpressionEvaluator();
    var evaluationResult = evaluator.evaluate(node);
    assertFalse(evaluationResult.getBooleanValue());
  }

  public void test_false() throws Exception {
    var node = new NotOperatorNode(location, "!");
    node.setNode(falseLiteral);
    var evaluator = new ExpressionEvaluator();
    var evaluationResult = evaluator.evaluate(node);
    assertTrue(evaluationResult.getBooleanValue());
  }

  public void test_null() throws Exception {
    var node = new NotOperatorNode(location, "!");
    node.setNode(nullLiteral);
    var evaluator = new ExpressionEvaluator();
    var evaluationResult = evaluator.evaluate(node);
    assertTrue(evaluationResult.getBooleanValue());
  }
}
