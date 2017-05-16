/**
 * Created by Dominik Schwarz on 18.03.2017.
 */
'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client').rest;
import {registerForNotification} from './client';

const CreateTransaction = require('./createTransactionComponent');


class App extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>

                <nav className="navbar navbar-default">
                    <div className="container-fluid">
                        <div className="navbar-header">
                            <a className="navbar-brand" href="#">Group6Bank</a>
                        </div>
                        <ul className="nav navbar-nav">
                            <li className="active"><a href="#">Übersicht</a></li>
                        </ul>
                        <ul className="nav navbar-nav navbar-right">
                            <li><UserInfo/></li>
                            <li><LogoutButton/></li>
                        </ul>
                    </div>
                </nav>

                <div className="container">
                    <CreateTransaction />
                </div>

                <div className="container">
                    <TransactionDetails/>
                </div>

                <Transactions/>

            </div>
        )
    }
}

class LogoutButton extends React.Component {

    constructor(props) {
        super(props);
    }

    logout(e) {
        e.preventDefault();
        client({method: 'POST', path: '/logout'}).done(response => {
            window.location.href = '/';
        });
    }

    render() {
        return (
            <a onClick={this.logout}><span className="glyphicon glyphicon-log-out"></span> Logout</a>
        )
    }
}

class UserInfo extends React.Component {

    constructor(props) {
        super(props);
        this.state = {name: 'no_name',balance:0};
    }
    componentWillMount() {

        client({method: 'GET', path: '/userinfo'}).done(response => {
            this.setState({name: response.entity.firstname+" "+response.entity.lastname});
            this.setState({balance: response.entity.balance});
        });
    }
    render() {
        return (
            <ul className="list-unstyled">
                <li><b>Benutzer: {this.state.name}</b></li>
                <li><b>Kontostand: {this.state.balance} €</b></li>
            </ul>
        )

    }
};

class TransactionRows extends React.Component {

    render() {
        /* the ES6 version of const data = this.props.data */
        const {data} = this.props;
        /*
         use map to perform the same function on
         each element in the obj array
         */

        const row = data.map((data) =>
            <tr>
                <td>{data.description}</td>
                <td>{data.value} €</td>
                <td>{data.owner.username}</td>
                <td>{data.target.username}</td>
                <td><TransactionButton transaction={data}/></td>
            </tr>
        );
        return (
            <div className="container">
                <h3>Transaktionen:</h3>
                <table className="table table-striped">
                    <tr>
                        <th>Beschreibung</th>
                        <th>Betrag</th>
                        <th>Auftraggeber</th>
                        <th>Begünstigter</th>
                        <th></th>
                    </tr>
                    {row}
                </table>
            </div>
        );
    }
}
class TransactionButton extends React.Component {
    constructor(props) {
        super(props);
        this.click = this.click.bind(this);
    }

    click(e) {
        e.preventDefault();
        TransactionDetails.showDetails(this.props.transaction);
    }

    render() {
        return (
            <div>
                <button type="button" className="btn btn-secondary" onClick={this.click}>Details</button>
            </div>
        )
    }
}

class Transactions extends React.Component {

    constructor(props) {
        super(props);
        this.state = {transactions: []};
        this.getTransactions = this.getTransactions.bind(this);
    }

    componentWillMount() {
        registerForNotification([
            {route: '/incomingTransaction', callback: this.getTransactions}
        ]);
        this.getTransactions();
    }

    getTransactions(){
        client({method: 'GET', path: '/userinfo'}).done(response => {
            this.setState({user: response.entity});
            client({method: 'GET', path: '/transactionsByAccountId?accountid='+response.entity.id}).done(response => {
                this.setState({transactions: response.entity});
            });
        });
    }

    render() {
        return (
            <div>
                <TransactionRows data={this.state.transactions} />
            </div>
        )
    }
}

class TransactionDetails extends React.Component {


    constructor(props) {
        super(props);
        this.state = {transaction: null};
        window.details = this;
    }
    static showDetails(newTransaction) {
        if(window.details!=null) {
            window.details.setState({transaction: newTransaction});
            window.details.forceUpdate();
        }

    }

    render() {
        if(this.state.transaction != null) {
            return (
                <div>
                    <h4>Transaktionsdetails</h4>
                    <ul className="list-group">
                        <li className="list-group-item">Betrag: {this.state.transaction.value} €</li>
                        <li className="list-group-item">Auftraggeber: {this.state.transaction.owner.firstname} {this.state.transaction.owner.lastname}</li>
                        <li className="list-group-item">Kontonummer Auftraggeber: {this.state.transaction.owner.accountNumber}</li>
                        <li className="list-group-item">Begünstigter: {this.state.transaction.target.firstname} {this.state.transaction.target.lastname}<br/></li>
                        <li className="list-group-item">Kontonummer Begünstigter: {this.state.transaction.target.accountNumber}</li>
                        <li className="list-group-item">Erstellt: {this.state.transaction.created}</li>
                        <li className="list-group-item">Ausgeführt: {this.state.transaction.commited}</li>
                    </ul>
                </div>
            );
        }
        else return (
            <div></div>
        );
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('react')
);

