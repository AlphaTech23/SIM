const french = {
  messages: {
    projectName: 'sim',
    common: {
      update: 'modifier',
      delete: 'supprimer',
      create: 'créer',
      apply: 'appliquer',
      go: 'aller',

      views: 'vues',
      view: 'vue',
      details: 'détails',
      list: 'liste',
      new: 'nouveau',
      actions: 'actions',
      back: 'retour',
      layout: 'mise en page',
      theme: 'thème',
      general: 'général',
      settings: 'paramètres',
      entities: 'entités',
      entity: 'entité',
      filter: 'filtre',
      rows: 'lignes',
      showing: 'affichage',
      previous: 'précédent',
      next: 'suivant',
      page: 'page',
      add: 'ajouter',
      description: 'description',
      return: 'retourner',
      home: 'accueil',
      login: 'connexion',
      logout: 'déconnexion',
      register: 'inscription',
      profile: 'profil',
      username: "nom d'utilisateur",
      email: 'email',
      password: 'mot de passe',
      language: 'langue',
      confirm: 'confirmer',
      cancel: 'annuler',
      no: 'aucun',
      read: 'lire',
      edit: 'Éditer',
      save: 'Sauvegarder',
    },
    box_confirm: {
      H1Se: 'Voulez-vous effacer cet élément définitivement?',
      H2Se: 'Pas de retour en arrière possible'
    },
    header: {
      backList: 'Revenir à la liste',
      list: 'Liste',
      details: 'Détails',
      add: "Formulaire d'ajout de",
      form: 'Formulaire'
    },
    banner: {
      lang: 'Langue',
      disconnect: "se déconnecter"
    },
    saying_words: {
      entries: "de nouvelles entrées",
      details: "les détails",
      information: "les informations",
      and: "et",
      needs: "ce dont vous n'avez pas besoin",
    },

    navbar: {
      core: 'Noyau',
      entities: 'ENTITES',
      views: 'VUES',
      settings: 'Parametres',
      home: 'Accueil',
      customisation: 'Personnalisation de layout',
    },

    authentication: {
      title: 'Se connecter',
      login: 'Connexion',
      logout: 'Se Deconnecter',
      register: 'Inscription',
      profile: 'Profile',
      username: 'Nom d’utilisateur',
      email: 'Email',
      password: 'Mot de passe',
      confirmPassword: 'Confirmer votre @:common.password',
      submit: 'Se connecter',
      invalid: 'Identifiants invalides',
      createAccount: 'Créer un compte',
      backToSignIn: 'Retour à la connexion',
      dontHaveAccount: "Vous n'avez pas de compte?",
      passwordPlaceholder: '••••••••',
    },
    
    register: {
      title: 'Créer un compte',
      name: 'Nom',
      email: 'Email',
      password: 'Mot de passe',
      submit: "S'inscrire",
      backToSignIn: 'Retour à la connexion',
      success: 'Compte créé avec succès',
      error: "Échec de l'inscription",
      haveAccount: 'Vous avez déjà un compte?',
    },

    home: {
      greet: 'Bonjour',
      greetDeveloper: '@.capitalize:home.greet Développeur',
      welcome: 'Bienvenue sur l’application frontend @.capitalize:projectName',
      titles: {
        entities: 'List des @:common.entities',
        views: 'List des @:common.views',
      },
      simple_welcome: 'Bienvenue',
      add: 'Ajouter',
      save: 'Enregistrer',
      cancel: 'Annuler',
      edit: 'Éditer',
    },

    pagination: {
      sizeLabel: 'Affichage:',
      previous: 'page @:common.previous',
      next: 'page @:common.next',
      goToLabel: 'Aller à la page',
      totalRows: ' sur {total} @:common.rows',
      pageOf: 'Page {page} sur {total}',
    },

    search: {
      filters: '@.capitalize:common.no @:common.filter | filter | filter ({count})',
      simple_filter: 'Filtres',
      selection: 'Choisir un champ',
    },

    entity: {
      nav: '{entity} / ',
      list: {
        nav: 'List',
        filters: 'Filtres',
        addFilter: 'Ajouter un filtre',
        removeFilter: 'Supprimer le filtre {{label}}',
      },
      create: {
        nav: 'Nouveau',
        submitLabel: 'Cree {entity}',
      },
      update: {
        nav: 'Modifier',
        submitLabel: 'Modifier {entity}',
      },
      details: {
        nav: 'Details',
      },
    },

    button: {
      addEntity: 'Cree un(e) @:common.new(elle) {entity}',
      applySearch: 'Appliquer',
      go: 'Aller',
      validate: 'Valider',
      delete: 'Supprimer',
      update: 'Modifier',
      create: 'Cree',
      cancel: 'Annuler',
      backTo: 'Retrour vers {name}',
      backToList: 'Retour à la @:common.list',
      backToListDescription: 'Retour à la @:common.view @:common.list',
      export: 'Exporter en CSV',
      back: 'Retour',
    },

    settings: {
      layout: 'Mise en page',
      theme: 'Them',
      general: 'General',
      language: 'Langue',
      pageTitle: 'Parametres',
      switchLayout: 'Changer de mise en page',
    },

    // Définitions d’entités (scalable)
    entities:{
      activite: {
        name: 'activite | activites',
        nav: {
          title: '@.capitalize:entities.activite.name',
          create: 'Create @.capitalize:entities.activite.name',
          list: '@.capitalize:entities.activite.name @:common.list',
        },
      },
      activitesMembre: {
        name: 'activitesMembre | activitesMembres',
        nav: {
          title: '@.capitalize:entities.activitesMembre.name',
          create: 'Create @.capitalize:entities.activitesMembre.name',
          list: '@.capitalize:entities.activitesMembre.name @:common.list',
        },
      },
      membre: {
        name: 'membre | membres',
        nav: {
          title: '@.capitalize:entities.membre.name',
          create: 'Create @.capitalize:entities.membre.name',
          list: '@.capitalize:entities.membre.name @:common.list',
        },
      },
      plageHoraire: {
        name: 'plageHoraire | plageHoraires',
        nav: {
          title: '@.capitalize:entities.plageHoraire.name',
          create: 'Create @.capitalize:entities.plageHoraire.name',
          list: '@.capitalize:entities.plageHoraire.name @:common.list',
        },
      },
      rendezVou: {
        name: 'rendezVou | rendezVous',
        nav: {
          title: '@.capitalize:entities.rendezVou.name',
          create: 'Create @.capitalize:entities.rendezVou.name',
          list: '@.capitalize:entities.rendezVou.name @:common.list',
        },
      },
      secteur: {
        name: 'secteur | secteurs',
        nav: {
          title: '@.capitalize:entities.secteur.name',
          create: 'Create @.capitalize:entities.secteur.name',
          list: '@.capitalize:entities.secteur.name @:common.list',
        },
      },
      statut: {
        name: 'statut | statuts',
        nav: {
          title: '@.capitalize:entities.statut.name',
          create: 'Create @.capitalize:entities.statut.name',
          list: '@.capitalize:entities.statut.name @:common.list',
        },
      },
      statutRendezVou: {
        name: 'statutRendezVou | statutRendezVous',
        nav: {
          title: '@.capitalize:entities.statutRendezVou.name',
          create: 'Create @.capitalize:entities.statutRendezVou.name',
          list: '@.capitalize:entities.statutRendezVou.name @:common.list',
        },
      },
      sujet: {
        name: 'sujet | sujets',
        nav: {
          title: '@.capitalize:entities.sujet.name',
          create: 'Create @.capitalize:entities.sujet.name',
          list: '@.capitalize:entities.sujet.name @:common.list',
        },
      },
      sujetsMembre: {
        name: 'sujetsMembre | sujetsMembres',
        nav: {
          title: '@.capitalize:entities.sujetsMembre.name',
          create: 'Create @.capitalize:entities.sujetsMembre.name',
          list: '@.capitalize:entities.sujetsMembre.name @:common.list',
        },
      },
      
    },
    views: {
      
    },

    error: {
      error: 'Erreur',
      400: 'Requête invalide',
      403: 'Accès interdit',
      404: {
        title: 'Page introuvable',
        description: "Désolé, la page que vous recherchez n'existe pas ou a été déplacée.",
      },
      500: 'Erreur interne (500)',
    },
    layout: {
      switchToHorizontal: "Passer à l'horizontal",
      switchToVertical: 'Passer à la vertical',
    },
    theme: {
      switchToLight: 'Mode clair',
      switchToDark: 'Mode sombre',
    },
    language: {
      choose: 'Langue',
      en: 'Anglais',
      fr: 'Français',
      es: 'Espagnol',
      de: 'Allemand',
      it: 'Italien',
      pt: 'Portugais',
      'pt-BR': 'Portugais (Brésil)',
      zh: 'Chinois',
      ja: 'Japonais',
    },
    state: {
      loading: 'Chargement…',
      notFound: 'Introuvable',
      success: 'Succès',
      creating: 'Création en cours…',
      updating: 'Mise à jour en cours…',
    },
    confirmation: {
      delete: 'Confirmer la suppression ?',
    },
    navigation: {
      list: 'Liste',
      add: 'Ajouter',
    },
    profile: {
      myProfile: 'Mon profil',
      edit: 'Modifier mon profil',
      changePassword: 'Changer mot de passe',
      oldPassword: 'Mot de passe actuel',
      newPassword: 'Nouveau mot de passe',
      confirmPassword: 'Confirmer mot de passe',
      updateSuccess: 'Profil mis à jour avec succès',
      updateError: 'Échec de la mise à jour',
      usernameChangedLogout: "Nom d'utilisateur modifié. Vous allez être déconnecté pour vous reconnecter avec vos nouveaux identifiants.",
      usernameChangeWarning: "⚠️ Attention : Changer votre nom d'utilisateur vous déconnectera. Vous devrez vous reconnecter avec vos nouveaux identifiants.",
      passwordChangeSuccess: 'Mot de passe modifié avec succès',
      passwordChangeError: 'Échec du changement de mot de passe',
      passwordMismatch: 'Les mots de passe ne correspondent pas',
    },
},

  dateTimeFormats: {
    short: { year: 'numeric', month: 'short', day: 'numeric' },
    long: { year: 'numeric', month: 'long', day: 'numeric', weekday: 'long' },
    time: { hour: '2-digit', minute: '2-digit' },
    full: {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      weekday: 'long',
      hour: '2-digit',
      minute: '2-digit',
    },
  },

  numberFormats: {
    currency: {
      style: 'currency',
      currency: 'EUR',
      currencyDisplay: 'symbol', // 1 234,56 €
    },
    decimal: {
      style: 'decimal',
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    },
    percent: {
      style: 'percent',
      useGrouping: true, // 88 %
    },
  },
}
  export default french
