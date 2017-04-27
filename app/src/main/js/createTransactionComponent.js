/**
 * Created by e1126 on 26.04.2017.
 */

const React = require('react');
const client = require('./client').rest;


var CreateTransaction =  React.createClass({

    getInitialState () {
        return {
            phase: 'create_transaction', transaction_to_commit: -1
        };
    },

    onCreateTransaction: function () {

        var value = document.getElementById('createTransactionForm').childNodes[1].value;
        var account = document.getElementById('createTransactionForm').childNodes[3].value;
        var description = document.getElementById('createTransactionForm').childNodes[5].value;

        client({method: 'POST', path: '/transfer?value='+value+'&account='+account+'&description='+description}).done(response => {
            this.setState({phase: 'confirm_transaction', transaction_to_commit: response.entity});
            
            document.getElementById('createTransactionForm').childNodes[1].value='';
            document.getElementById('createTransactionForm').childNodes[3].value='';
            document.getElementById('createTransactionForm').childNodes[5].value='';
        });
    },

    onCommitTransaction: function() {

        var tan = document.getElementById('commitTransactionForm').childNodes[1].value;
        client({method: 'POST', path: '/commit?tan='+tan+'&transactionId='+this.state.transaction_to_commit}).done(response => {
            this.setState({phase: 'create_transaction', transaction_to_commit: -1});
            document.getElementById('commitTransactionForm').childNodes[1].value='';
        });
    },

    render: function() {
        if(this.state.phase == 'create_transaction') {
            return (
                <div id="createTransactionForm">
                    <a>Value</a><input type="number" name="value" />
                    <a>Target Account</a><input type="number" name="target_account" />
                    <a>Description</a><input type="text" name="description" />
                    <button onClick={this.onCreateTransaction}>Send Transaction</button>
                </div>
            );
        } else if(this.state.phase == 'confirm_transaction') {
            return (
                <div id="commitTransactionForm">
                    <a>Enter TAN for Transaction {this.state.transaction_to_commit}</a>
                    <input type="text" />
                    <button onClick={this.onCommitTransaction}>Confirm Transaction</button>
                </div>
            );
        }

    }
});

module.exports = CreateTransaction;