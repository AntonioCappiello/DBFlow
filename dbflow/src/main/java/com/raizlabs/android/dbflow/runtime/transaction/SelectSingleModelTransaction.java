package com.raizlabs.android.dbflow.runtime.transaction;

import com.raizlabs.android.dbflow.runtime.DBTransactionInfo;
import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.ConditionGroup;
import com.raizlabs.android.dbflow.sql.language.SQLCondition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.language.property.IProperty;
import com.raizlabs.android.dbflow.sql.queriable.ModelQueriable;
import com.raizlabs.android.dbflow.structure.Model;

/**
 * Description: Runs a fetch on the {@link com.raizlabs.android.dbflow.runtime.DBTransactionQueue}, returning only the first item.
 */
public class SelectSingleModelTransaction<ModelClass extends Model> extends BaseResultTransaction<ModelClass> {

    private ModelQueriable<ModelClass> modelQueriable;

    /**
     * Creates an instance of this class
     *
     * @param transactionListener The transaction listener.
     * @param tableClass          The table to select from.
     * @param whereConditions     The conditions to use in the SELECT query.
     */
    public SelectSingleModelTransaction(Class<ModelClass> tableClass,
                                        TransactionListener<ModelClass> transactionListener, SQLCondition... whereConditions) {
        this(new Select().from(tableClass).where(whereConditions), transactionListener);
    }

    /**
     * Creates this class with a {@link com.raizlabs.android.dbflow.sql.language.From}
     *
     * @param modelQueriable      The model queriable that defines how to fetch models from the DB.
     * @param transactionListener The transaction listener.
     */
    public SelectSingleModelTransaction(ModelQueriable<ModelClass> modelQueriable, TransactionListener<ModelClass> transactionListener) {
        super(DBTransactionInfo.createFetch(), transactionListener);
        this.modelQueriable = modelQueriable;
    }

    /**
     * Creates an instance of this class
     *
     * @param transactionListener        The result that returns from this query.
     * @param whereConditionQueryBuilder The query builder used to SELECT.
     * @param properties                 The columns to project the SELECT on.
     */
    public SelectSingleModelTransaction(TransactionListener<ModelClass> transactionListener,
                                        Class<ModelClass> table, ConditionGroup conditionGroup, IProperty... properties) {
        this(new Select(properties).from(table).where(conditionGroup), transactionListener);
    }

    @Override
    public ModelClass onExecute() {
        return modelQueriable.querySingle();
    }
}
